package com.lframework.xingyun.sc.api.bo.purchase;

import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.basedata.facade.entity.Supplier;
import com.lframework.xingyun.sc.facade.dto.purchase.PurchaseOrderWithReceiveDto;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PurchaseOrderWithReceiveBo extends BaseBo<PurchaseOrderWithReceiveDto> {

  /**
   * 订单ID
   */
  @ApiModelProperty("订单ID")
  private String id;

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

  /**
   * 仓库名称
   */
  @ApiModelProperty("仓库名称")
  private String scName;

  /**
   * 供应商ID
   */
  @ApiModelProperty("供应商ID")
  private String supplierId;

  /**
   * 供应商名称
   */
  @ApiModelProperty("供应商名称")
  private String supplierName;

  /**
   * 采购员ID
   */
  @ApiModelProperty("采购员ID")
  private String purchaserId;

  /**
   * 采购员姓名
   */
  @ApiModelProperty("采购员姓名")
  private String purchaserName;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<DetailBo> details;

  public PurchaseOrderWithReceiveBo() {

  }

  public PurchaseOrderWithReceiveBo(PurchaseOrderWithReceiveDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<PurchaseOrderWithReceiveDto> convert(PurchaseOrderWithReceiveDto dto) {

    return super.convert(dto, PurchaseOrderWithReceiveBo::getPurchaserId,
        PurchaseOrderWithReceiveBo::getDetails);
  }

  @Override
  protected void afterInit(PurchaseOrderWithReceiveDto dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterFeignClient.findById(dto.getScId()).getData();
    this.scName = sc.getName();

    SupplierFeignClient suppliesupplierFeignClientService = ApplicationUtil.getBean(
        SupplierFeignClient.class);
    Supplier supplier = suppliesupplierFeignClientService.findById(dto.getSupplierId()).getData();
    this.supplierName = supplier.getName();

    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      IUserService userService = ApplicationUtil.getBean(IUserService.class);
      UserDto purchaser = userService.findById(dto.getPurchaserId());

      this.purchaserId = purchaser.getId();
      this.purchaserName = purchaser.getName();
    }

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(DetailBo::new).collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class DetailBo extends BaseBo<PurchaseOrderWithReceiveDto.DetailDto> {

    /**
     * ID
     */
    @ApiModelProperty("ID")
    private String id;

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
     * SKU编号
     */
    @ApiModelProperty("SKU编号")
    private String skuCode;

    /**
     * 外部编号
     */
    @ApiModelProperty("外部编号")
    private String externalCode;

    /**
     * 单位
     */
    @ApiModelProperty("单位")
    private String unit;

    /**
     * 规格
     */
    @ApiModelProperty("规格")
    private String spec;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String categoryName;

    /**
     * 品牌名称
     */
    @ApiModelProperty("品牌名称")
    private String brandName;

    /**
     * 销售属性1
     */
    @ApiModelProperty("销售属性1")
    private String salePropItemName1;

    /**
     * 销售属性2
     */
    @ApiModelProperty("销售属性2")
    private String salePropItemName2;

    /**
     * 采购数量
     */
    @ApiModelProperty("采购数量")
    private Integer orderNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 剩余收货数量
     */
    @ApiModelProperty("剩余收货数量")
    private Integer remainNum;

    /**
     * 是否赠品
     */
    @ApiModelProperty("是否赠品")
    private Boolean isGift;

    /**
     * 税率（%）
     */
    @ApiModelProperty("税率（%）")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    public DetailBo() {

    }

    public DetailBo(PurchaseOrderWithReceiveDto.DetailDto dto) {

      super(dto);
    }

    @Override
    public BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(
        PurchaseOrderWithReceiveDto.DetailDto dto) {

      return this;
    }

    @Override
    public <A> BaseBo<PurchaseOrderWithReceiveDto.DetailDto> convert(
        PurchaseOrderWithReceiveDto.DetailDto dto, SFunction<A, ?>... columns) {

      return this;
    }

    @Override
    protected void afterInit(PurchaseOrderWithReceiveDto.DetailDto dto) {

      ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);
      PurchaseProductDto product = productFeignClient.getPurchaseById(dto.getProductId()).getData();

      this.id = dto.getId();
      this.productId = product.getId();
      this.productCode = product.getCode();
      this.productName = product.getName();
      this.skuCode = product.getSkuCode();
      this.externalCode = product.getExternalCode();
      this.unit = product.getUnit();
      this.spec = product.getSpec();
      this.categoryName = product.getCategoryName();
      this.brandName = product.getBrandName();
      if (product.getSaleProps() != null) {
        this.salePropItemName1 = product.getSaleProps().getItemName1();
        this.salePropItemName2 = product.getSaleProps().getItemName2();
      }

      this.orderNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();
      this.remainNum = NumberUtil.sub(dto.getOrderNum(), dto.getReceiveNum()).intValue();
      this.isGift = dto.getIsGift();
      this.taxRate = dto.getTaxRate();
      this.description = dto.getDescription();
    }
  }
}
