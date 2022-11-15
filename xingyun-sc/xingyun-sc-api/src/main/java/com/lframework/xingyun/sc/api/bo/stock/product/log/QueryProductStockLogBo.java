package com.lframework.xingyun.sc.api.bo.stock.product.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.NumberUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.ProductSalePropItemRelationFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.ProductDto;
import com.lframework.xingyun.basedata.facade.dto.product.saleprop.item.SalePropItemByProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.sc.biz.service.stock.IProductLotService;
import com.lframework.xingyun.sc.facade.entity.ProductLot;
import com.lframework.xingyun.sc.facade.entity.ProductStockLog;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockLogBo extends BaseBo<ProductStockLog> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 批次号
   */
  @ApiModelProperty("批次号")
  private String lotCode;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库编号
   */
  @ApiModelProperty("仓库编号")
  private String scCode;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 商品ID
   */
  @ApiModelProperty("商品ID")
  private String productId;

  /**
   * 商品编号
   */
  @ApiModelProperty("商品编号")
  private String productCode;

  /**
   * 商品名称
   */
  @ApiModelProperty("商品名称")
  private String productName;

  /**
   * 商品类目
   */
  @ApiModelProperty("商品类目")
  private String categoryName;

  /**
   * 商品品牌
   */
  @ApiModelProperty("商品品牌")
  private String brandName;

  /**
   * 销售属性1
   */
  @ApiModelProperty("销售属性1")
  private String salePropItem1;

  /**
   * 销售属性2
   */
  @ApiModelProperty("销售属性2")
  private String salePropItem2;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 供应商编号
   */
  @ApiModelProperty("供应商编号")
  private String supplierCode;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

  /**
   * 库存数量
   */
  @ApiModelProperty("库存数量")
  private Integer stockNum;

  /**
   * 原库存数量
   */
  @ApiModelProperty("原库存数量")
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  @ApiModelProperty("现库存数量")
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  @ApiModelProperty("原含税成本价")
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  @ApiModelProperty("现含税成本价")
  private BigDecimal curTaxPrice;

  /**
   * 原无税成本价
   */
  @ApiModelProperty("原无税成本价")
  private BigDecimal oriUnTaxPrice;

  /**
   * 现无税成本价
   */
  @ApiModelProperty("现无税成本价")
  private BigDecimal curUnTaxPrice;

  /**
   * 含税金额
   */
  @ApiModelProperty("含税金额")
  private BigDecimal taxAmount;

  /**
   * 无税金额
   */
  @ApiModelProperty("无税金额")
  private BigDecimal unTaxAmount;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  @ApiModelProperty("业务单据ID")
  private String bizId;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String bizCode;

  /**
   * 业务类型
   */
  @ApiModelProperty("业务类型")
  private Integer bizType;

  public QueryProductStockLogBo() {

  }

  public QueryProductStockLogBo(ProductStockLog dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<ProductStockLog> convert(ProductStockLog dto) {

    return super.convert(dto, QueryProductStockLogBo::getBizType);
  }

  @Override
  protected void afterInit(ProductStockLog dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();

    IProductLotService productLotService = ApplicationUtil.getBean(IProductLotService.class);
    ProductLot lot = productLotService.findById(dto.getLotId());

    this.lotCode = lot.getLotCode();

    SupplierFeignClient supplierFeignClient = ApplicationUtil.getBean(SupplierFeignClient.class);
    Supplier supplier = supplierFeignClient.findById(lot.getSupplierId()).getData();
    this.supplierCode = supplier.getCode();
    this.supplierName = supplier.getName();

    ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);
    ProductDto product = productFeignClient.findById(dto.getProductId()).getData();
    this.productCode = product.getCode();
    this.productName = product.getName();
    this.categoryName = product.getPoly().getCategoryName();
    this.brandName = product.getPoly().getBrandName();
    if (product.getPoly().getMultiSaleProp()) {
      ProductSalePropItemRelationFeignClient productSalePropItemRelationFeignClient = ApplicationUtil.getBean(
          ProductSalePropItemRelationFeignClient.class);
      SalePropItemByProductDto saleProps = productSalePropItemRelationFeignClient.getByProductId(
          product.getId()).getData();
      this.salePropItem1 = saleProps.getItemName1();
      this.salePropItem2 = saleProps.getItemName2();
    }

    this.oriTaxPrice = NumberUtil.getNumber(dto.getOriTaxPrice(), 2);
    this.curTaxPrice = NumberUtil.getNumber(dto.getCurTaxPrice(), 2);
    this.oriUnTaxPrice = NumberUtil.getNumber(dto.getOriUnTaxPrice(), 2);
    this.curUnTaxPrice = NumberUtil.getNumber(dto.getCurUnTaxPrice(), 2);
    this.taxAmount = NumberUtil.getNumber(dto.getTaxAmount(), 2);
    this.unTaxAmount = NumberUtil.getNumber(dto.getUnTaxAmount(), 2);

    this.bizType = dto.getBizType().getCode();
  }
}
