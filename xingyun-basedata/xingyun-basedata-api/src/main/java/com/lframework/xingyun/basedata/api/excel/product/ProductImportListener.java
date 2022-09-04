package com.lframework.xingyun.basedata.api.excel.product;

import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.components.excel.ExcelImportListener;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolySalePropGroupService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPolyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductPurchaseService;
import com.lframework.xingyun.basedata.biz.service.product.IProductRetailService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemRelationService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSalePropItemService;
import com.lframework.xingyun.basedata.biz.service.product.IProductSaleService;
import com.lframework.xingyun.basedata.biz.service.product.IProductService;
import com.lframework.xingyun.basedata.facade.entity.Product;
import com.lframework.xingyun.basedata.facade.entity.ProductPoly;
import com.lframework.xingyun.basedata.facade.entity.ProductPolySalePropGroup;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItemRelation;
import com.lframework.xingyun.basedata.facade.vo.product.info.saleprop.CreateProductSalePropItemRelationVo;
import com.lframework.xingyun.basedata.facade.vo.product.purchase.CreateProductPurchaseVo;
import com.lframework.xingyun.basedata.facade.vo.product.purchase.UpdateProductPurchaseVo;
import com.lframework.xingyun.basedata.facade.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.facade.vo.product.retail.UpdateProductRetailVo;
import com.lframework.xingyun.basedata.facade.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.facade.vo.product.sale.UpdateProductSaleVo;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductImportListener extends ExcelImportListener<ProductImportModel> {

  @Override
  protected void doInvoke(ProductImportModel data, AnalysisContext context) {
    if (StringUtil.isBlank(data.getSpuCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品货号”不能为空");
    }
    IProductPolyService productPolyService = ApplicationUtil.getBean(IProductPolyService.class);
    Wrapper<ProductPoly> queryPolyWrapper = Wrappers.lambdaQuery(ProductPoly.class)
        .eq(ProductPoly::getCode, data.getSpuCode());
    ProductPoly poly = productPolyService.getOne(queryPolyWrapper);
    if (poly == null) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品货号”不存在");
    }
    data.setPolyId(poly.getId());
    if (StringUtil.isBlank(data.getCode())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品编号”不能为空");
    }
    if (!RegUtil.isMatch(PatternPool.PATTERN_CODE, data.getCode())) {
      throw new DefaultClientException(
          "第" + context.readRowHolder().getRowIndex() + "行“商品编号”必须由字母、数字、“-_.”组成，长度不能超过20位");
    }
    if (StringUtil.isBlank(data.getName())) {
      throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行“商品名称”不能为空");
    }

    if (!poly.getMultiSaleprop()) {
      if (!StringUtil.isBlank(data.getSalePropItemCode1()) || !StringUtil.isBlank(
          data.getSalePropItemCode2())) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行商品不是多销售属性商品，不能指定销售属性编号");
      }
    } else {
      IProductSalePropItemService productSalePropItemService = ApplicationUtil.getBean(
          IProductSalePropItemService.class);

      IProductPolySalePropGroupService productPolySalePropGroupService = ApplicationUtil.getBean(
          IProductPolySalePropGroupService.class);
      List<ProductPolySalePropGroup> salePropGroups = productPolySalePropGroupService.getByPolyId(
          poly.getId());
      if (salePropGroups.size() > 0) {
        Wrapper<ProductSalePropItem> querySalePropItemWrapper = Wrappers.lambdaQuery(
                ProductSalePropItem.class)
            .eq(ProductSalePropItem::getGroupId, salePropGroups.get(0).getSalePropGroupId())
            .eq(ProductSalePropItem::getCode, data.getSalePropItemCode1());
        ProductSalePropItem salePropItem = productSalePropItemService.getOne(
            querySalePropItemWrapper);
        if (salePropItem == null) {
          throw new DefaultClientException(
              "第" + context.readRowHolder().getRowIndex() + "行“销售属性1编号”不存在");
        }
        data.setSalePropItemId1(salePropItem.getId());
      }
      if (salePropGroups.size() > 1) {
        Wrapper<ProductSalePropItem> querySalePropItemWrapper = Wrappers.lambdaQuery(
                ProductSalePropItem.class)
            .eq(ProductSalePropItem::getGroupId, salePropGroups.get(1).getSalePropGroupId())
            .eq(ProductSalePropItem::getCode, data.getSalePropItemCode2());
        ProductSalePropItem salePropItem = productSalePropItemService.getOne(
            querySalePropItemWrapper);
        if (salePropItem == null) {
          throw new DefaultClientException(
              "第" + context.readRowHolder().getRowIndex() + "行“销售属性2编号”不存在");
        }
        data.setSalePropItemId2(salePropItem.getId());
      }
      if (salePropGroups.size() == 0 || salePropGroups.size() > 2) {
        throw new DefaultClientException("第" + context.readRowHolder().getRowIndex() + "行销售属性数据有误");
      }
    }

    IProductService productService = ApplicationUtil.getBean(IProductService.class);
    Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
        .eq(Product::getCode, data.getCode());
    Product product = productService.getOne(queryWrapper);
    if (product == null) {
      if (StringUtil.isBlank(data.getSkuCode())) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“商品SKU编号”不能为空");
      }
    } else {
      if (!product.getPolyId().equals(poly.getId())) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“商品编号”不属于“商品货号”，请检查");
      }
    }

    if (!StringUtil.isBlank(data.getSkuCode())) {
      LambdaQueryWrapper<Product> checkSkuCodeWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getSkuCode, data.getSkuCode());
      if (product != null) {
        checkSkuCodeWrapper.ne(Product::getId, product.getId());
      }

      if (productService.count(checkSkuCodeWrapper) > 0) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“商品SKU编号”重复，请检查");
      }
    }

    if (data.getPurchasePrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getPurchasePrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getPurchasePrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“采购价（元）”不允许小于0");
      }
    }

    if (data.getSalePrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getSalePrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getSalePrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“销售价（元）”不允许小于0");
      }
    }

    if (data.getRetailPrice() != null) {
      if (!NumberUtil.isNumberPrecision(data.getRetailPrice(), 2)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”最多允许2位小数");
      }
      if (NumberUtil.lt(data.getRetailPrice(), 0)) {
        throw new DefaultClientException(
            "第" + context.readRowHolder().getRowIndex() + "行“零售价（元）”不允许小于0");
      }
    }
  }

  @Override
  protected void afterAllAnalysed(AnalysisContext context) {

    IProductService productService = ApplicationUtil.getBean(IProductService.class);

    IProductSalePropItemRelationService productSalePropItemRelationService = ApplicationUtil.getBean(
        IProductSalePropItemRelationService.class);

    List<ProductImportModel> datas = this.getDatas();
    for (int i = 0; i < datas.size(); i++) {
      ProductImportModel data = datas.get(i);

      Wrapper<Product> checkSkuCodeWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getSkuCode, data.getSkuCode()).ne(Product::getCode, data.getCode());
      if (productService.count(checkSkuCodeWrapper) > 0) {
        throw new DefaultClientException("第" + (i + 1) + "行“商品SKU编号”重复，请重新输入");
      }

      boolean isInsert = false;
      Wrapper<Product> queryWrapper = Wrappers.lambdaQuery(Product.class)
          .eq(Product::getCode, data.getCode());
      Product record = productService.getOne(queryWrapper);
      if (record == null) {
        record = new Product();

        record.setId(IdUtil.getId());
        isInsert = true;
      }

      record.setCode(data.getCode());
      record.setName(data.getName());
      record.setPolyId(data.getPolyId());
      record.setSkuCode(data.getSkuCode());
      record.setExternalCode(data.getExternalCode());
      record.setSpec(data.getSpec());
      record.setUnit(data.getUnit());

      if (isInsert) {
        record.setAvailable(Boolean.TRUE);
      }

      productService.saveOrUpdate(record);
      data.setId(record.getId());

      CreateProductSalePropItemRelationVo createProductSalePropItemRelationVo = new CreateProductSalePropItemRelationVo();
      createProductSalePropItemRelationVo.setProductId(data.getId());
      List<String> salePropItems = new ArrayList<>(2);
      if (!StringUtil.isEmpty(data.getSalePropItemId1())) {
        salePropItems.add(data.getSalePropItemId1());
      }

      if (!StringUtil.isEmpty(data.getSalePropItemId2())) {
        salePropItems.add(data.getSalePropItemId2());
      }

      createProductSalePropItemRelationVo.setSalePropItemIds(salePropItems);

      if (!CollectionUtil.isEmpty(salePropItems)) {
        LambdaQueryWrapper<ProductSalePropItemRelation> checkRelationWrapper = Wrappers.lambdaQuery(
                ProductSalePropItemRelation.class)
            .eq(ProductSalePropItemRelation::getPolyId, record.getPolyId())
            .eq(ProductSalePropItemRelation::getSalePropItemId1, salePropItems.get(0));
        if (salePropItems.size() > 1) {
          checkRelationWrapper.eq(ProductSalePropItemRelation::getSalePropItemId2,
              salePropItems.get(1));
        }
        checkRelationWrapper.ne(ProductSalePropItemRelation::getProductId, record.getId());
        if (productSalePropItemRelationService.count(checkRelationWrapper) > 0) {
          throw new DefaultClientException("第" + (i + 1) + "行“销售属性1、销售属性2”已设置商品，同一组销售属性不允许设置多个商品");
        }
        productSalePropItemRelationService.create(createProductSalePropItemRelationVo);
      }

      if (data.getPurchasePrice() != null) {
        IProductPurchaseService productPurchaseService = ApplicationUtil.getBean(
            IProductPurchaseService.class);
        if (isInsert) {
          CreateProductPurchaseVo createProductPurchaseVo = new CreateProductPurchaseVo();
          createProductPurchaseVo.setId(data.getId());
          createProductPurchaseVo.setPrice(data.getPurchasePrice());

          productPurchaseService.create(createProductPurchaseVo);
        } else {
          UpdateProductPurchaseVo updateProductPurchaseVo = new UpdateProductPurchaseVo();
          updateProductPurchaseVo.setId(data.getId());
          updateProductPurchaseVo.setPrice(data.getPurchasePrice());

          productPurchaseService.update(updateProductPurchaseVo);
        }
      }

      if (data.getSalePrice() != null) {
        IProductSaleService productSaleService = ApplicationUtil.getBean(IProductSaleService.class);
        if (isInsert) {
          CreateProductSaleVo createProductSaleVo = new CreateProductSaleVo();
          createProductSaleVo.setId(data.getId());
          createProductSaleVo.setPrice(data.getSalePrice());

          productSaleService.create(createProductSaleVo);
        } else {
          UpdateProductSaleVo updateProductSaleVo = new UpdateProductSaleVo();
          updateProductSaleVo.setId(data.getId());
          updateProductSaleVo.setPrice(data.getSalePrice());

          productSaleService.update(updateProductSaleVo);
        }
      }

      if (data.getRetailPrice() != null) {
        IProductRetailService productRetailService = ApplicationUtil.getBean(
            IProductRetailService.class);
        if (isInsert) {
          CreateProductRetailVo createProductRetailVo = new CreateProductRetailVo();
          createProductRetailVo.setId(data.getId());
          createProductRetailVo.setPrice(data.getRetailPrice());

          productRetailService.create(createProductRetailVo);
        } else {
          UpdateProductRetailVo updateProductRetailVo = new UpdateProductRetailVo();
          updateProductRetailVo.setId(data.getId());
          updateProductRetailVo.setPrice(data.getRetailPrice());

          productRetailService.update(updateProductRetailVo);
        }
      }

      this.setSuccessProcess(i);
    }
  }

  @Override
  protected void doComplete() {
    IProductSalePropItemRelationService productSalePropItemRelationService = ApplicationUtil.getBean(
        IProductSalePropItemRelationService.class);
    IProductService productService = ApplicationUtil.getBean(IProductService.class);
    for (ProductImportModel data : this.getDatas()) {
      productService.cleanCacheByKey(data.getId());
      productSalePropItemRelationService.cleanCacheByKey(data.getId());
    }
  }
}
