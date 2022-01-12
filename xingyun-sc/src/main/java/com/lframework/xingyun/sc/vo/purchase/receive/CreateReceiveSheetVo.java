package com.lframework.xingyun.sc.vo.purchase.receive;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.NumberUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.service.purchase.IReceiveSheetService;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateReceiveSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @NotBlank(message = "仓库ID不能为空！")
    private String scId;

    /**
     * 供应商ID
     */
    @NotBlank(message = "供应商ID不能为空！")
    private String supplierId;

    /**
     * 采购员ID
     */
    private String purchaserId;

    /**
     * 付款日期
     */
    private LocalDate paymentDate;

    /**
     * 到货日期
     */
    @NotNull(message = "到货日期不能为空！")
    private LocalDate receiveDate;

    /**
     * 采购订单ID
     */
    private String purchaseOrderId;

    /**
     * 商品信息
     */
    @Valid
    @NotEmpty(message = "商品不能为空！")
    private List<ReceiveProductVo> products;

    /**
     * 备注
     */
    private String description;

    @Override
    public void validate() {

        IPurchaseConfigService purchaseConfigService = ApplicationUtil.getBean(IPurchaseConfigService.class);
        PurchaseConfigDto purchaseConfig = purchaseConfigService.get();

        this.validate(purchaseConfig.getReceiveRequirePurchase());
    }

    protected void validate(boolean requirePurchase) {

        IReceiveSheetService receiveSheetService = ApplicationUtil.getBean(IReceiveSheetService.class);
        GetPaymentDateDto paymentDate = receiveSheetService.getPaymentDate(this.supplierId);
        if (paymentDate.getAllowModify()) {
            if (this.getPaymentDate() == null) {
                throw new InputErrorException("付款日期不能为空！");
            }
        }

        if (requirePurchase) {
            if (StringUtil.isBlank(this.getPurchaseOrderId())) {
                throw new InputErrorException("采购订单不能为空！");
            }
        }

        int orderNo = 1;
        for (ReceiveProductVo product : this.products) {

            if (StringUtil.isBlank(product.getProductId())) {
                throw new InputErrorException("第" + orderNo + "行商品不能为空！");
            }

            if (product.getReceiveNum() == null) {
                throw new InputErrorException("第" + orderNo + "行商品收货数量不能为空！");
            }

            if (product.getReceiveNum() <= 0) {
                throw new InputErrorException("第" + orderNo + "行商品收货数量必须大于0！");
            }

            if (!requirePurchase) {
                if (product.getPurchasePrice() == null) {
                    throw new InputErrorException("第" + orderNo + "行商品采购价不能为空！");
                }

                if (product.getPurchasePrice().doubleValue() < 0D) {
                    throw new InputErrorException("第" + orderNo + "行商品采购价不允许小于0！");
                }

                if (!NumberUtil.isNumberPrecision(product.getPurchasePrice(), 2)) {
                    throw new InputErrorException("第" + orderNo + "行商品采购价最多允许2位小数！");
                }
            }

            orderNo++;
        }

        if (requirePurchase) {
            if (this.products.stream().allMatch(t -> StringUtil.isBlank(t.getPurchaseOrderDetailId()))) {
                throw new InputErrorException("采购订单中的商品必须全部或部分收货！");
            }
        }
    }
}
