package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.facade.dto.product.info.GetProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.PreTakeStockProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.RetailProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.info.TakeStockSheetProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.facade.entity.Product;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.facade.enums.ProductCategoryNodeType;
import com.lframework.xingyun.basedata.biz.mappers.ProductMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.biz.service.product.IProductRetailService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSaleService;
import com.lframework.xingyun.basedata.biz.service.product.IProductService;
import com.lframework.xingyun.basedata.facade.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryPreTakeStockProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryPurchaseProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryRetailProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QuerySaleProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.QueryTakeStockSheetProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.UpdateProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
import com.lframework.xingyun.basedata.facade.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.facade.vo.product.purchase.UpdateProductPurchaseVo;
import com.lframework.xingyun.basedata.facade.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.facade.vo.product.retail.UpdateProductRetailVo;
import com.lframework.xingyun.basedata.facade.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.facade.vo.product.sale.UpdateProductSaleVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends BaseMpServiceImpl<ProductMapper, Product> implements
    IProductService {

  @Autowired
  private IProductPolyService productPolyService;

  @Autowired
  private IProductPurchaseService productPurchaseService;

  @Autowired
  private IProductSaleService productSaleService;

  @Autowired
  private IProductRetailService productRetailService;

  @Autowired
  private IProductSalePropItemRelationService productSalePropItemRelationService;

  @Autowired
  private IRecursionMappingService recursionMappingService;

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  @Override
  public PageResult<ProductDto> query(Integer pageIndex, Integer pageSize, QueryProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductDto> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductDto> query(QueryProductVo vo) {

    List<ProductDto> datas = getBaseMapper().query(vo);
    if (!CollectionUtil.isEmpty(datas)) {
      datas.forEach(this::convertDto);
    }

    return datas;
  }

  @Override
  public Integer queryCount(QueryProductVo vo) {

    return getBaseMapper().queryCount(vo);
  }

  @Cacheable(value = ProductDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductDto findById(String id) {

    ProductDto data = getBaseMapper().findById(id);
    return this.convertDto(data);
  }

  @Override
  public GetProductDto getDetailById(String id) {

    return getBaseMapper().getDetailById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用商品，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getAvailable, Boolean.FALSE).in(Product::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用商品，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getAvailable, Boolean.TRUE).in(Product::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductVo vo) {

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(Product.class).eq(Product::getSkuCode, vo.getSkuCode());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("商品SKU编号重复，请重新输入！");
    }

    Product data = new Product();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setPolyId(vo.getPolyId());
    data.setSkuCode(vo.getSkuCode());
    if (!StringUtil.isBlank(vo.getExternalCode())) {
      data.setExternalCode(vo.getExternalCode());
    }

    if (!StringUtil.isBlank(vo.getSpec())) {
      data.setSpec(vo.getSpec());
    }

    if (!StringUtil.isBlank(vo.getUnit())) {
      data.setUnit(vo.getUnit());
    }
    data.setAvailable(Boolean.TRUE);

    getBaseMapper().insert(data);

    if (vo.getPurchasePrice() == null) {
      throw new DefaultClientException("采购价不能为空！");
    }

    if (vo.getPurchasePrice().doubleValue() < 0D) {
      throw new DefaultClientException("采购价不允许小于0！");
    }

    CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
    createProductPurchaseVo.setId(data.getId());
    createProductPurchaseVo.setPrice(vo.getPurchasePrice());

    productPurchaseService.create(createProductPurchaseVo);

    if (vo.getSalePrice() == null) {
      throw new DefaultClientException("销售价不能为空！");
    }

    if (vo.getSalePrice().doubleValue() < 0D) {
      throw new DefaultClientException("销售价不允许小于0！");
    }

    CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
    createProductSaleVo.setId(data.getId());
    createProductSaleVo.setPrice(vo.getSalePrice());

    productSaleService.create(createProductSaleVo);

    if (vo.getRetailPrice() == null) {
      throw new DefaultClientException("零售价不能为空！");
    }

    if (vo.getRetailPrice().doubleValue() < 0D) {
      throw new DefaultClientException("零售价不允许小于0！");
    }

    CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
    createProductRetailVo.setId(data.getId());
    createProductRetailVo.setPrice(vo.getRetailPrice());

    productRetailService.create(createProductRetailVo);

    if (!CollectionUtil.isEmpty(vo.getSalePropItems())) {
      CreateProductSalePropItemRelationVo createProductSalePropItemRelationVo = new CreateProductSalePropItemRelationVo();
      createProductSalePropItemRelationVo.setProductId(data.getId());
      createProductSalePropItemRelationVo.setSalePropItemIds(vo.getSalePropItems());

      productSalePropItemRelationService.create(createProductSalePropItemRelationVo);
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改商品，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductVo vo) {

    Product data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品不存在！");
    }

    Wrapper<Product> checkWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, vo.getCode()).ne(Product::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    checkWrapper = Wrappers.lambdaQuery(Product.class).eq(Product::getSkuCode, vo.getSkuCode())
        .ne(Product::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("商品SKU编号重复，请重新输入！");
    }

    LambdaUpdateWrapper<Product> updateWrapper = Wrappers.lambdaUpdate(Product.class)
        .set(Product::getCode, vo.getCode()).set(Product::getName, vo.getName())
        .set(Product::getAvailable, vo.getAvailable()).set(Product::getSkuCode, vo.getSkuCode())
        .set(Product::getExternalCode,
            StringUtil.isBlank(vo.getExternalCode()) ? null : vo.getExternalCode())
        .set(Product::getSpec, StringUtil.isBlank(vo.getSpec()) ? null : vo.getSpec())
        .set(Product::getUnit, StringUtil.isBlank(vo.getUnit()) ? null : vo.getUnit())
        .eq(Product::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    ProductPoly poly = productPolyService.getById(data.getPolyId());
    if (poly.getMultiSaleprop()) {
      Wrapper<ProductSalePropItemRelation> queryRelationWrapper = Wrappers.lambdaQuery(
              ProductSalePropItemRelation.class)
          .eq(ProductSalePropItemRelation::getProductId, data.getId());
      ProductSalePropItemRelation relation = productSalePropItemRelationService.getOne(
          queryRelationWrapper);

      String salePropGroup1Id = relation.getSalePropGroupId1();
      Wrapper<ProductSalePropItem> queryItemWrapper = Wrappers.lambdaQuery(
              ProductSalePropItem.class).eq(ProductSalePropItem::getGroupId, salePropGroup1Id)
          .eq(ProductSalePropItem::getId, vo.getSalePropItem1Id());

      if (productSalePropItemService.count(queryItemWrapper) != 1) {
        throw new DefaultClientException("销售属性1不存在！");
      }

      String salePropGroup2Id = relation.getSalePropGroupId2();
      if (!StringUtil.isBlank(salePropGroup2Id)) {
        queryItemWrapper = Wrappers.lambdaQuery(ProductSalePropItem.class)
            .eq(ProductSalePropItem::getGroupId, salePropGroup2Id)
            .eq(ProductSalePropItem::getId, vo.getSalePropItem2Id());

        if (productSalePropItemService.count(queryItemWrapper) != 1) {
          throw new DefaultClientException("销售属性2不存在！");
        }
      }

      List<String> salePropItemIds = new ArrayList<>();
      salePropItemIds.add(vo.getSalePropItem1Id());
      if (!StringUtil.isBlank(salePropGroup2Id)) {
        salePropItemIds.add(vo.getSalePropItem2Id());
      }

      CreateProductSalePropItemRelationVo createProductSalePropItemRelationVo = new CreateProductSalePropItemRelationVo();
      createProductSalePropItemRelationVo.setProductId(data.getId());
      createProductSalePropItemRelationVo.setSalePropItemIds(salePropItemIds);

      productSalePropItemRelationService.create(createProductSalePropItemRelationVo);
    }

    productPurchaseService.removeById(data.getId());

    if (vo.getPurchasePrice() != null) {

      UpdateProductPurchaseVo updateProductPurchaseVo = new UpdateProductPurchaseVo();
      updateProductPurchaseVo.setId(data.getId());
      updateProductPurchaseVo.setPrice(vo.getPurchasePrice());

      productPurchaseService.update(updateProductPurchaseVo);
    }

    productSaleService.removeById(data.getId());

    if (vo.getSalePrice() != null) {
      UpdateProductSaleVo updateProductSaleVo = new UpdateProductSaleVo();
      updateProductSaleVo.setId(data.getId());
      updateProductSaleVo.setPrice(vo.getSalePrice());

      productSaleService.update(updateProductSaleVo);
    }

    productRetailService.removeById(data.getId());
    if (vo.getRetailPrice() != null) {
      UpdateProductRetailVo updateProductRetailVo = new UpdateProductRetailVo();
      updateProductRetailVo.setId(data.getId());
      updateProductRetailVo.setPrice(vo.getRetailPrice());

      productRetailService.update(updateProductRetailVo);
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @Override
  public PageResult<PurchaseProductDto> queryPurchaseByCondition(Integer pageIndex,
      Integer pageSize, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PurchaseProductDto> datas = getBaseMapper().queryPurchaseByCondition(condition);
    PageResult<PurchaseProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (PurchaseProductDto data : datas) {
        SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(saleProps);
      }
    }

    return pageResult;
  }

  @Override
  public PageResult<PurchaseProductDto> queryPurchaseList(Integer pageIndex, Integer pageSize,
      QueryPurchaseProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PurchaseProductDto> datas = getBaseMapper().queryPurchaseList(vo);
    PageResult<PurchaseProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (PurchaseProductDto data : datas) {
        SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(saleProps);
      }
    }

    return pageResult;
  }

  @Override
  public PurchaseProductDto getPurchaseById(String id) {

    PurchaseProductDto data = getBaseMapper().getPurchaseById(id);
    if (data != null) {
      SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
          data.getId());
      data.setSaleProps(saleProps);
    }

    return data;
  }

  @Override
  public PageResult<SaleProductDto> querySaleByCondition(Integer pageIndex, Integer pageSize,
      String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SaleProductDto> datas = getBaseMapper().querySaleByCondition(condition);
    PageResult<SaleProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (SaleProductDto data : datas) {
        SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(saleProps);
      }
    }

    return pageResult;
  }

  @Override
  public PageResult<SaleProductDto> querySaleList(Integer pageIndex, Integer pageSize,
      QuerySaleProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<SaleProductDto> datas = getBaseMapper().querySaleList(vo);
    PageResult<SaleProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (SaleProductDto data : datas) {
        SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(saleProps);
      }
    }

    return pageResult;
  }

  @Override
  public SaleProductDto getSaleById(String id) {

    SaleProductDto data = getBaseMapper().getSaleById(id);
    if (data != null) {
      SalePropItemByProductDto saleProps = productSalePropItemRelationService.getByProductId(
          data.getId());
      data.setSaleProps(saleProps);
    }

    return data;
  }

  @Override
  public PageResult<RetailProductDto> queryRetailByCondition(Integer pageIndex, Integer pageSize,
      String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<RetailProductDto> datas = getBaseMapper().queryRetailByCondition(condition);
    PageResult<RetailProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (RetailProductDto data : datas) {
        SalePropItemByProductDto retailProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(retailProps);
      }
    }

    return pageResult;
  }

  @Override
  public PageResult<RetailProductDto> queryRetailList(Integer pageIndex, Integer pageSize,
      QueryRetailProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<RetailProductDto> datas = getBaseMapper().queryRetailList(vo);
    PageResult<RetailProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    if (!CollectionUtil.isEmpty(datas)) {
      for (RetailProductDto data : datas) {
        SalePropItemByProductDto retailProps = productSalePropItemRelationService.getByProductId(
            data.getId());
        data.setSaleProps(retailProps);
      }
    }

    return pageResult;
  }

  @Override
  public RetailProductDto getRetailById(String id) {

    RetailProductDto data = getBaseMapper().getRetailById(id);
    if (data != null) {
      SalePropItemByProductDto retailProps = productSalePropItemRelationService.getByProductId(
          data.getId());
      data.setSaleProps(retailProps);
    }

    return data;
  }

  @Override
  public PageResult<PreTakeStockProductDto> queryPreTakeStockByCondition(Integer pageIndex,
      Integer pageSize, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PreTakeStockProductDto> datas = getBaseMapper().queryPreTakeStockByCondition(condition);
    PageResult<PreTakeStockProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<PreTakeStockProductDto> queryPreTakeStockList(Integer pageIndex,
      Integer pageSize, QueryPreTakeStockProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<PreTakeStockProductDto> datas = getBaseMapper().queryPreTakeStockList(vo);
    PageResult<PreTakeStockProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public List<ProductDto> getByCategoryIds(List<String> categoryIds) {

    if (CollectionUtil.isEmpty(categoryIds)) {
      return Collections.EMPTY_LIST;
    }

    // 根据categoryIds查询所有叶子节点
    List<String> children = new ArrayList<>();
    for (String categoryId : categoryIds) {
      children.addAll(recursionMappingService.getNodeChildIds(categoryId,
          ApplicationUtil.getBean(ProductCategoryNodeType.class)));
    }

    children.addAll(categoryIds);

    children = children.stream().distinct().collect(Collectors.toList());

    List<ProductDto> datas = getBaseMapper().getByCategoryIds(children);
    if (!CollectionUtil.isEmpty(datas)) {
      datas.forEach(this::convertDto);
    }

    return datas;
  }

  @Override
  public List<ProductDto> getByBrandIds(List<String> brandIds) {

    if (CollectionUtil.isEmpty(brandIds)) {
      return Collections.EMPTY_LIST;
    }

    List<ProductDto> datas = getBaseMapper().getByBrandIds(brandIds);
    if (!CollectionUtil.isEmpty(datas)) {
      datas.forEach(this::convertDto);
    }

    return datas;
  }

  @Override
  public PageResult<TakeStockSheetProductDto> queryTakeStockByCondition(Integer pageIndex,
      Integer pageSize, String planId, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<TakeStockSheetProductDto> datas = getBaseMapper().queryTakeStockByCondition(planId,
        condition);
    PageResult<TakeStockSheetProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<TakeStockSheetProductDto> queryTakeStockList(Integer pageIndex,
      Integer pageSize, QueryTakeStockSheetProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<TakeStockSheetProductDto> datas = getBaseMapper().queryTakeStockList(vo);
    PageResult<TakeStockSheetProductDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<StockCostAdjustProductDto> queryStockCostAdjustByCondition(Integer pageIndex,
      Integer pageSize, String scId, String condition) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<StockCostAdjustProductDto> datas = getBaseMapper().queryStockCostAdjustByCondition(scId,
        condition);
    PageResult<StockCostAdjustProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  @Override
  public PageResult<StockCostAdjustProductDto> queryStockCostAdjustList(Integer pageIndex,
      Integer pageSize, QueryStockCostAdjustProductVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<StockCostAdjustProductDto> datas = getBaseMapper().queryStockCostAdjustList(vo);
    PageResult<StockCostAdjustProductDto> pageResult = PageResultUtil.convert(
        new PageInfo<>(datas));

    return pageResult;
  }

  private ProductDto convertDto(ProductDto dto) {

    if (dto == null) {
      return dto;
    }

    ProductPolyDto poly = productPolyService.findById(dto.getPoly().getId());
    dto.setPoly(poly);

    return dto;
  }

  @CacheEvict(value = ProductDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
