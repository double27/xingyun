package com.lframework.xingyun.api.bo.sale.returned;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.product.info.SaleProductDto;
import com.lframework.xingyun.basedata.service.customer.ICustomerService;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.service.storecenter.IStoreCenterService;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDetailLotDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnFullDto;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetDetailLotService;
import com.lframework.xingyun.sc.service.sale.ISaleOutSheetService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSaleReturnBo extends BaseBo<SaleReturnFullDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 单号
     */
    private String code;

    /**
     * 仓库ID
     */
    private String scId;

    /**
     * 仓库名称
     */
    private String scName;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 销售员ID
     */
    private String salerId;

    /**
     * 销售员姓名
     */
    private String salerName;

    /**
     * 付款日期
     */
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
    private LocalDate paymentDate;

    /**
     * 销售出库单ID
     */
    private String outSheetId;

    /**
     * 销售出库单号
     */
    private String outSheetCode;

    /**
     * 退货数量
     */
    private Integer totalNum;

    /**
     * 赠品数量
     */
    private Integer giftNum;

    /**
     * 退货金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 审核人
     */
    private String approveBy;

    /**
     * 审核时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime approveTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 拒绝原因
     */
    private String refuseReason;

    /**
     * 结算状态
     */
    private Integer settleStatus;

    /**
     * 订单明细
     */
    private List<ReturnDetailBo> details;

    public GetSaleReturnBo(SaleReturnFullDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<SaleReturnFullDto> convert(SaleReturnFullDto dto) {

        return super.convert(dto, GetSaleReturnBo::getStatus, GetSaleReturnBo::getSettleStatus,
                GetSaleReturnBo::getDetails);
    }

    @Override
    protected void afterInit(SaleReturnFullDto dto) {

        IStoreCenterService storeCenterService = ApplicationUtil.getBean(IStoreCenterService.class);
        this.scName = storeCenterService.getById(dto.getScId()).getName();

        ICustomerService customerService = ApplicationUtil.getBean(ICustomerService.class);
        this.customerName = customerService.getById(dto.getCustomerId()).getName();

        IUserService userService = ApplicationUtil.getBean(IUserService.class);
        if (!StringUtil.isBlank(dto.getSalerId())) {
            this.salerName = userService.getById(dto.getSalerId()).getName();
        }

        ISaleOutSheetService saleOutSheetService = ApplicationUtil.getBean(ISaleOutSheetService.class);
        if (!StringUtil.isBlank(dto.getOutSheetId())) {
            SaleOutSheetDto outSheet = saleOutSheetService.getById(dto.getOutSheetId());
            this.outSheetCode = outSheet.getCode();
        }

        this.createBy = userService.getById(dto.getCreateBy()).getName();

        if (!StringUtil.isBlank(dto.getApproveBy())) {
            this.approveBy = userService.getById(dto.getApproveBy()).getName();
        }

        this.status = dto.getStatus().getCode();
        this.settleStatus = dto.getSettleStatus().getCode();

        this.totalNum = dto.getTotalNum();
        this.giftNum = dto.getTotalGiftNum();
        this.totalAmount = dto.getTotalAmount();

        if (!CollectionUtil.isEmpty(dto.getDetails())) {
            this.details = dto.getDetails().stream().map(t -> new ReturnDetailBo(this.getScId(), t))
                    .collect(Collectors.toList());
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class ReturnDetailBo extends BaseBo<SaleReturnFullDto.ReturnDetailDto> {

        /**
         * 明细ID
         */
        private String id;

        /**
         * 商品ID
         */
        private String productId;

        /**
         * 供应商ID
         */
        private String supplierId;

        /**
         * 供应商名称
         */
        private String supplierName;

        /**
         * 商品编号
         */
        private String productCode;

        /**
         * 商品名称
         */
        private String productName;

        /**
         * SKU编号
         */
        private String skuCode;

        /**
         * 外部编号
         */
        private String externalCode;

        /**
         * 单位
         */
        private String unit;

        /**
         * 规格
         */
        private String spec;

        /**
         * 类目名称
         */
        private String categoryName;

        /**
         * 品牌名称
         */
        private String brandName;

        /**
         * 销售属性1
         */
        private String salePropItemName1;

        /**
         * 销售属性2
         */
        private String salePropItemName2;

        /**
         * 出库数量
         */
        private Integer outNum;

        /**
         * 剩余退货数量
         */
        private Integer remainNum;

        /**
         * 退货数量
         */
        private Integer returnNum;

        /**
         * 原价
         */
        private BigDecimal salePrice;

        /**
         * 价格
         */
        private BigDecimal taxPrice;

        /**
         * 折扣
         */
        private BigDecimal discountRate;

        /**
         * 是否赠品
         */
        private Boolean isGift;

        /**
         * 税率
         */
        private BigDecimal taxRate;

        /**
         * 备注
         */
        private String description;

        /**
         * 销售出库单明细ID
         */
        private String outSheetDetailId;

        /**
         * 仓库ID
         */
        @JsonIgnore
        private String scId;

        public ReturnDetailBo(String scId, SaleReturnFullDto.ReturnDetailDto dto) {

            this.scId = scId;
            if (dto != null) {
                this.convert(dto);

                this.afterInit(dto);
            }
        }

        @Override
        public BaseBo<SaleReturnFullDto.ReturnDetailDto> convert(SaleReturnFullDto.ReturnDetailDto dto) {

            return super.convert(dto);
        }

        @Override
        protected void afterInit(SaleReturnFullDto.ReturnDetailDto dto) {

            this.returnNum = dto.getReturnNum();
            this.salePrice = dto.getOriPrice();
            this.taxPrice = dto.getTaxPrice();
            this.discountRate = dto.getDiscountRate();

            this.supplierId = dto.getSupplierId();
            ISupplierService supplierService = ApplicationUtil.getBean(ISupplierService.class);
            this.supplierName = supplierService.getById(dto.getSupplierId()).getName();

            IProductService productService = ApplicationUtil.getBean(IProductService.class);
            SaleProductDto product = productService.getSaleById(dto.getProductId());

            this.productCode = product.getCode();
            this.productName = product.getName();
            this.skuCode = product.getSkuCode();
            this.externalCode = product.getExternalCode();
            this.unit = product.getUnit();
            this.spec = product.getSpec();
            this.categoryName = product.getCategoryName();
            this.brandName = product.getBrandName();
            if (!CollectionUtil.isEmpty(product.getSaleProps())) {
                if (product.getSaleProps().size() > 0) {
                    this.salePropItemName1 = product.getSaleProps().get(0).getName();
                }

                if (product.getSaleProps().size() > 1) {
                    this.salePropItemName2 = product.getSaleProps().get(1).getName();
                }
            }

            if (!StringUtil.isBlank(dto.getOutSheetDetailId())) {
                ISaleOutSheetDetailLotService receiveSheetDetailService = ApplicationUtil
                        .getBean(ISaleOutSheetDetailLotService.class);
                SaleOutSheetDetailLotDto outSheetDetail = receiveSheetDetailService.getById(dto.getOutSheetDetailId());
                this.outNum = outSheetDetail.getOrderNum();
                this.remainNum = NumberUtil.sub(outSheetDetail.getOrderNum(), outSheetDetail.getReturnNum()).intValue();
            }
        }
    }
}
