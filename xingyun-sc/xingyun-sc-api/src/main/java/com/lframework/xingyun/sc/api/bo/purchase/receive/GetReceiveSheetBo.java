package com.lframework.xingyun.sc.api.bo.purchase.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.SupplierFeignClient;
import com.lframework.xingyun.basedata.facade.dto.product.info.PurchaseProductDto;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderDetailService;
import com.lframework.xingyun.sc.biz.service.purchase.IPurchaseOrderService;
import com.lframework.xingyun.sc.biz.service.stock.IProductStockService;
import com.lframework.xingyun.sc.facade.dto.purchase.receive.ReceiveSheetFullDto;
import com.lframework.xingyun.sc.facade.entity.ProductStock;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrder;
import com.lframework.xingyun.sc.facade.entity.PurchaseOrderDetail;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetReceiveSheetBo extends BaseBo<ReceiveSheetFullDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 单号
   */
  @ApiModelProperty("单号")
  private String code;

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
   * 付款日期
   */
  @ApiModelProperty("付款日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate paymentDate;

  /**
   * 到货日期
   */
  @ApiModelProperty("到货日期")
  @JsonFormat(pattern = StringPool.DATE_PATTERN)
  private LocalDate receiveDate;

  /**
   * 采购订单ID
   */
  @ApiModelProperty("采购订单ID")
  private String purchaseOrderId;

  /**
   * 采购订单号
   */
  @ApiModelProperty("采购订单号")
  private String purchaseOrderCode;

  /**
   * 采购数量
   */
  @ApiModelProperty("采购数量")
  private Integer totalNum;

  /**
   * 赠品数量
   */
  @ApiModelProperty("赠品数量")
  private Integer giftNum;

  /**
   * 采购金额
   */
  @ApiModelProperty("采购金额")
  private BigDecimal totalAmount;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

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
   * 审核人
   */
  @ApiModelProperty("审核人")
  private String approveBy;

  /**
   * 审核时间
   */
  @ApiModelProperty("审核时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime approveTime;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer status;

  /**
   * 拒绝原因
   */
  @ApiModelProperty("拒绝原因")
  private String refuseReason;

  /**
   * 结算状态
   */
  @ApiModelProperty("结算状态")
  private Integer settleStatus;

  /**
   * 订单明细
   */
  @ApiModelProperty("订单明细")
  private List<OrderDetailBo> details;

  public GetReceiveSheetBo() {

  }

  public GetReceiveSheetBo(ReceiveSheetFullDto dto) {

    super(dto);
  }

  @Override
  public BaseBo<ReceiveSheetFullDto> convert(ReceiveSheetFullDto dto) {

    return super.convert(dto, GetReceiveSheetBo::getStatus, GetReceiveSheetBo::getSettleStatus,
        GetReceiveSheetBo::getDetails);
  }

  @Override
  protected void afterInit(ReceiveSheetFullDto dto) {

    StoreCenterFeignClient storeCenterFeignClient = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    this.scName = storeCenterFeignClient.findById(dto.getScId()).getData().getName();

    SupplierFeignClient supplierFeignClient = ApplicationUtil.getBean(SupplierFeignClient.class);
    this.supplierName = supplierFeignClient.findById(dto.getSupplierId()).getData().getName();

    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    if (!StringUtil.isBlank(dto.getPurchaserId())) {
      this.purchaserName = userService.findById(dto.getPurchaserId()).getName();
    }

    IPurchaseOrderService purchaseOrderService = ApplicationUtil.getBean(
        IPurchaseOrderService.class);
    if (!StringUtil.isBlank(dto.getPurchaseOrderId())) {
      PurchaseOrder purchaseOrder = purchaseOrderService.getById(dto.getPurchaseOrderId());
      this.purchaseOrderCode = purchaseOrder.getCode();
    }

    if (!StringUtil.isBlank(dto.getApproveBy())) {
      this.approveBy = userService.findById(dto.getApproveBy()).getName();
    }

    this.status = dto.getStatus().getCode();
    this.settleStatus = dto.getSettleStatus().getCode();

    this.totalNum = dto.getTotalNum();
    this.giftNum = dto.getTotalGiftNum();
    this.totalAmount = dto.getTotalAmount();

    if (!CollectionUtil.isEmpty(dto.getDetails())) {
      this.details = dto.getDetails().stream().map(t -> new OrderDetailBo(this.getScId(), t))
          .collect(Collectors.toList());
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class OrderDetailBo extends BaseBo<ReceiveSheetFullDto.OrderDetailDto> {

    /**
     * 明细ID
     */
    @ApiModelProperty("明细ID")
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
     * 剩余收货数量
     */
    @ApiModelProperty("剩余收货数量")
    private Integer remainNum;

    /**
     * 收货数量
     */
    @ApiModelProperty("收货数量")
    private Integer receiveNum;

    /**
     * 采购价
     */
    @ApiModelProperty("采购价")
    private BigDecimal purchasePrice;

    /**
     * 含税成本价
     */
    @ApiModelProperty("含税成本价")
    private BigDecimal taxCostPrice;

    /**
     * 库存数量
     */
    @ApiModelProperty("库存数量")
    private Integer stockNum;

    /**
     * 是否赠品
     */
    @ApiModelProperty("是否赠品")
    private Boolean isGift;

    /**
     * 税率
     */
    @ApiModelProperty("税率")
    private BigDecimal taxRate;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String description;

    /**
     * 采购订单明细ID
     */
    @ApiModelProperty("采购订单明细ID")
    private String purchaseOrderDetailId;

    /**
     * 仓库ID
     */
    @ApiModelProperty(value = "仓库ID", hidden = true)
    @JsonIgnore
    private String scId;

    public OrderDetailBo(String scId, ReceiveSheetFullDto.OrderDetailDto dto) {

      this.scId = scId;
      if (dto != null) {
        this.convert(dto);

        this.afterInit(dto);
      }
    }

    @Override
    public BaseBo<ReceiveSheetFullDto.OrderDetailDto> convert(
        ReceiveSheetFullDto.OrderDetailDto dto) {

      return super.convert(dto);
    }

    @Override
    protected void afterInit(ReceiveSheetFullDto.OrderDetailDto dto) {

      this.receiveNum = dto.getOrderNum();
      this.purchasePrice = dto.getTaxPrice();

      ProductFeignClient productFeignClient = ApplicationUtil.getBean(ProductFeignClient.class);
      PurchaseProductDto product = productFeignClient.getPurchaseById(dto.getProductId()).getData();

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

      if (!StringUtil.isBlank(dto.getPurchaseOrderDetailId())) {
        IPurchaseOrderDetailService purchaseOrderDetailService = ApplicationUtil.getBean(
            IPurchaseOrderDetailService.class);
        PurchaseOrderDetail purchaseOrderDetail = purchaseOrderDetailService.getById(
            dto.getPurchaseOrderDetailId());
        this.orderNum = purchaseOrderDetail.getOrderNum();
        this.remainNum = NumberUtil.sub(purchaseOrderDetail.getOrderNum(),
            purchaseOrderDetail.getReceiveNum()).intValue();
      }

      IProductStockService productStockService = ApplicationUtil.getBean(
          IProductStockService.class);
      ProductStock productStock = productStockService.getByProductIdAndScId(this.getProductId(),
          this.getScId());
      this.taxCostPrice = productStock == null ? BigDecimal.ZERO
          : NumberUtil.getNumber(productStock.getTaxPrice(), 2);
      this.stockNum = productStock == null ? 0 : productStock.getStockNum();
    }
  }
}
