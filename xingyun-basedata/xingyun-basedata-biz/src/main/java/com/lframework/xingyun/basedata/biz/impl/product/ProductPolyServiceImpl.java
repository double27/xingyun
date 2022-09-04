package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.CacheUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.xingyun.basedata.biz.mappers.ProductPolyMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyPropertyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolySalePropGroupService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPropertyItemService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPropertyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropGroupService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.biz.service.product.IProductService;
import com.lframework.xingyun.basedata.facade.dto.product.poly.ProductPolyDto;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import com.lframework.xingyun.basedata.facade.entity.ProductProperty;
import com.lframework.xingyun.basedata.facade.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.enums.ColumnType;
import com.lframework.xingyun.basedata.facade.vo.product.info.CreateProductVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.CreateProductPolyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.QueryProductPolyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.UpdateProductPolyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.property.CreateProductPolyPropertyVo;
import com.lframework.xingyun.basedata.facade.vo.product.poly.saleprop.CreateProductPolySalePropGroupVo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ProductPolyServiceImpl extends BaseMpServiceImpl<ProductPolyMapper, ProductPoly>
    implements IProductPolyService {

  @Autowired
  private IProductPolyPropertyService productPolyPropertyService;

  @Autowired
  private IProductCategoryService productCategoryService;

  @Autowired
  private IProductBrandService productBrandService;

  @Autowired
  private IProductPropertyService productPropertyService;

  @Autowired
  private IProductPropertyItemService productPropertyItemService;

  @Autowired
  private IProductService productService;

  @Autowired
  private IProductPolySalePropGroupService productPolySalePropGroupService;

  @Autowired
  private IProductSalePropGroupService productSalePropGroupService;

  @Autowired
  private IProductSalePropItemService productSalePropItemService;

  @Override
  public PageResult<ProductPoly> query(Integer pageIndex, Integer pageSize,
      QueryProductPolyVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductPoly> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductPoly> query(QueryProductPolyVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public ProductPolyDto findById(String id) {

    ProductPolyDto data = CacheUtil.get(ProductPolyDto.CACHE_NAME, id, ProductPolyDto.class);
    if (data == null) {
      data = getBaseMapper().findById(id);
      if (data == null) {
        return data;
      }

      CacheUtil.put(ProductPolyDto.CACHE_NAME, id, data);
    }

    return convertDto(data);
  }

  @Override
  public List<String> getIdNotInPolyProperty(String propertyId) {

    return getBaseMapper().getIdNotInPolyProperty(propertyId);
  }

  @Override
  public List<String> getIdByCategoryId(String categoryId) {

    return getBaseMapper().getIdByCategoryId(categoryId);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品SPU，ID：{}, 货号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductPolyVo vo) {

    Wrapper<ProductPoly> checkCodeWrapper = Wrappers.lambdaQuery(ProductPoly.class)
        .eq(ProductPoly::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("商品货号重复，请重新输入！");
    }

    Wrapper<ProductPoly> checkNameWrapper = Wrappers.lambdaQuery(ProductPoly.class)
        .eq(ProductPoly::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("商品名称重复，请重新输入！");
    }

    ProductPoly poly = new ProductPoly();
    poly.setId(IdUtil.getId());
    poly.setCode(vo.getCode());
    poly.setName(vo.getName());
    poly.setShortName(
        StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName());

    ProductCategory productCategory = productCategoryService.findById(vo.getCategoryId());
    if (productCategory == null) {
      throw new DefaultClientException("商品类目不存在！");
    }
    poly.setCategoryId(vo.getCategoryId());

    ProductBrand productBrand = productBrandService.findById(vo.getBrandId());
    if (productBrand == null) {
      throw new DefaultClientException("商品品牌不存在！");
    }
    poly.setBrandId(vo.getBrandId());
    poly.setMultiSaleprop(vo.getMultipleSaleProp());
    poly.setTaxRate(vo.getTaxRate());
    poly.setSaleTaxRate(vo.getSaleTaxRate());

    getBaseMapper().insert(poly);

    //建立poly和属性值的关系
    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      for (CreateProductPolyVo.PropertyVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.findById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("商品属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("商品属性值不存在！");
          }

          CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
          createProductPolyPropertyVo.setPolyId(poly.getId());
          createProductPolyPropertyVo.setPropertyId(productProperty.getId());
          createProductPolyPropertyVo.setPropertyItemId(propertyItem.getId());

          productPolyPropertyService.create(createProductPolyPropertyVo);
        } else if (productProperty.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
            createProductPolyPropertyVo.setPolyId(poly.getId());
            createProductPolyPropertyVo.setPropertyId(productProperty.getId());
            createProductPolyPropertyVo.setPropertyItemId(propertyItemId);

            productPolyPropertyService.create(createProductPolyPropertyVo);
          }

        } else if (productProperty.getColumnType() == ColumnType.CUSTOM) {

          CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
          createProductPolyPropertyVo.setPolyId(poly.getId());
          createProductPolyPropertyVo.setPropertyId(productProperty.getId());
          createProductPolyPropertyVo.setPropertyText(property.getText());
          productPolyPropertyService.create(createProductPolyPropertyVo);
        } else {
          throw new DefaultClientException("商品属性字段类型不存在！");
        }
      }
    }

    // 建立poly和销售属性组的关系
    CreateProductPolySalePropGroupVo createProductPolySalePropGroupVo = new CreateProductPolySalePropGroupVo();
    createProductPolySalePropGroupVo.setPolyId(poly.getId());
    Set<String> salePropGroupIds = new LinkedHashSet<>();

    //创建商品
    int orderNo = 1;
    for (CreateProductPolyVo.ProductVo product : vo.getProducts()) {
      CreateProductVo createProductVo = new CreateProductVo();
      createProductVo.setCode(product.getCode());
      createProductVo.setName(product.getName());
      createProductVo.setPolyId(poly.getId());
      createProductVo.setSkuCode(product.getSkuCode());
      createProductVo.setExternalCode(product.getExternalCode());
      createProductVo.setSpec(product.getSpec());
      createProductVo.setUnit(product.getUnit());
      createProductVo.setPurchasePrice(product.getPurchasePrice());
      createProductVo.setSalePrice(product.getSalePrice());
      createProductVo.setRetailPrice(product.getRetailPrice());

      if (vo.getMultipleSaleProp()) {
        List<String> salePropItems = new ArrayList<>();
        salePropItems.add(product.getSalePropItemId1());

        ProductSalePropItem salePropItem1 = productSalePropItemService.getById(
            product.getSalePropItemId1());
        ProductSalePropGroup salePropGroup1 = productSalePropGroupService.getById(
            salePropItem1.getGroupId());
        salePropGroupIds.add(salePropGroup1.getId());

        if (!StringUtil.isBlank(product.getSalePropItemId2())) {
          salePropItems.add(product.getSalePropItemId2());

          ProductSalePropItem salePropItem2 = productSalePropItemService.getById(
              product.getSalePropItemId2());
          ProductSalePropGroup salePropGroup2 = productSalePropGroupService.getById(
              salePropItem2.getGroupId());
          salePropGroupIds.add(salePropGroup2.getId());
        }

        createProductVo.setSalePropItems(salePropItems);
      }

      if (salePropGroupIds.size() > 2) {
        throw new DefaultClientException("销售属性数据有误！");
      }

      createProductPolySalePropGroupVo.setSalePropGroupIds(new ArrayList<>(salePropGroupIds));
      productPolySalePropGroupService.create(createProductPolySalePropGroupVo);

      try {
        productService.create(createProductVo);
      } catch (ClientException e) {
        throw new DefaultClientException("第" + orderNo + "行商品新增失败，具体原因：" + e.getMsg());
      }

      orderNo++;
    }

    OpLogUtil.setVariable("id", poly.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return poly.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改商品SPU，ID：{}", params = {"#id"})
  @Transactional
  @Override
  public void update(UpdateProductPolyVo vo) {

    ProductPoly data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("商品SPU不存在！");
    }

    LambdaUpdateWrapper<ProductPoly> updateWrapper = Wrappers.lambdaUpdate(ProductPoly.class)
        .set(ProductPoly::getCode, vo.getCode())
        .set(ProductPoly::getName, vo.getName())
        .set(ProductPoly::getShortName, vo.getShortName())
        .set(ProductPoly::getCategoryId, vo.getCategoryId())
        .set(ProductPoly::getBrandId, vo.getBrandId())
        .set(ProductPoly::getTaxRate, vo.getTaxRate())
        .set(ProductPoly::getSaleTaxRate, vo.getSaleTaxRate())
        .eq(ProductPoly::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    productPolyPropertyService.deleteByPolyId(data.getId());

    //建立poly和属性值的关系
    if (!CollectionUtil.isEmpty(vo.getProperties())) {
      for (UpdateProductPolyVo.PropertyVo property : vo.getProperties()) {
        ProductProperty productProperty = productPropertyService.getById(property.getId());
        if (productProperty == null) {
          throw new DefaultClientException("商品属性不存在！");
        }
        if (productProperty.getColumnType() == ColumnType.SINGLE) {
          ProductPropertyItem propertyItem = productPropertyItemService.findById(
              property.getText());
          if (propertyItem == null) {
            throw new DefaultClientException("商品属性值不存在！");
          }

          CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
          createProductPolyPropertyVo.setPolyId(data.getId());
          createProductPolyPropertyVo.setPropertyId(productProperty.getId());
          createProductPolyPropertyVo.setPropertyItemId(propertyItem.getId());

          productPolyPropertyService.create(createProductPolyPropertyVo);
        } else if (productProperty.getColumnType() == ColumnType.MULTIPLE) {

          List<String> propertyItemIds = JsonUtil.parseList(property.getText(), String.class);
          for (String propertyItemId : propertyItemIds) {
            CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
            createProductPolyPropertyVo.setPolyId(data.getId());
            createProductPolyPropertyVo.setPropertyId(productProperty.getId());
            createProductPolyPropertyVo.setPropertyItemId(propertyItemId);

            productPolyPropertyService.create(createProductPolyPropertyVo);
          }

        } else if (productProperty.getColumnType() == ColumnType.CUSTOM) {

          CreateProductPolyPropertyVo createProductPolyPropertyVo = new CreateProductPolyPropertyVo();
          createProductPolyPropertyVo.setPolyId(data.getId());
          createProductPolyPropertyVo.setPropertyId(productProperty.getId());
          createProductPolyPropertyVo.setPropertyText(property.getText());
          productPolyPropertyService.create(createProductPolyPropertyVo);
        } else {
          throw new DefaultClientException("商品属性字段类型不存在！");
        }
      }
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  private ProductPolyDto convertDto(ProductPolyDto dto) {

    if (dto == null) {
      return dto;
    }

    ProductBrand brand = productBrandService.findById(dto.getBrandId());
    dto.setBrandName(brand.getName());

    ProductCategory category = productCategoryService.findById(dto.getCategoryId());
    dto.setCategoryName(category.getName());

    dto.setProperties(productPolyPropertyService.getByPolyId(dto.getId()));

    return dto;
  }

  @CacheEvict(value = ProductPolyDto.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
