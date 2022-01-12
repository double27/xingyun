package com.lframework.xingyun.basedata.vo.product.poly;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateProductPolyVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品货号
     */
    @NotBlank(message = "商品货号不能为空！")
    private String code;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空！")
    private String name;

    /**
     * 商品简称
     */
    private String shortName;

    /**
     * 类目ID
     */
    @NotBlank(message = "请选择商品类目！")
    private String categoryId;

    /**
     * 品牌ID
     */
    @NotBlank(message = "请选择商品品牌！")
    private String brandId;

    /**
     * 是否多销售属性
     */
    @NotNull(message = "请选择是否多销售属性！")
    private Boolean multipleSaleProp;

    /**
     * 进项税率（%）
     */
    @NotNull(message = "进项税率（%）不能为空！")
    @Min(value = 0, message = "进项税率（%）不允许小于0！")
    @Digits(integer = 10, fraction = 0, message = "进项税率（%）必须为整数！")
    private BigDecimal taxRate;

    /**
     * 销项税率（%）
     */
    @NotNull(message = "销项税率（%）不能为空！")
    @Min(value = 0, message = "销项税率（%）不允许小于0！")
    @Digits(integer = 10, fraction = 0, message = "销项税率（%）必须为整数！")
    private BigDecimal saleTaxRate;

    /**
     * 商品属性
     */
    @Valid
    @NotEmpty
    private List<PropertyVo> properties;

    /**
     * 商品信息
     */
    @Valid
    @NotEmpty
    private List<ProductVo> products;

    @Override
    public void validate() {

        int orderNo = 1;
        for (ProductVo product : this.getProducts()) {
            if (this.getMultipleSaleProp()) {
                if (StringUtil.isBlank(product.getSalePropItemId1())) {
                    throw new InputErrorException("销售属性1不能为空！");
                }
            }

            if (StringUtil.isBlank(product.getCode())) {
                throw new InputErrorException("第" + (orderNo) + "行商品编号不能为空！");
            }

            if (StringUtil.isBlank(product.getName())) {
                throw new InputErrorException("第" + (orderNo) + "行商品名称不能为空！");
            }

            if (StringUtil.isBlank(product.getSkuCode())) {
                throw new InputErrorException("第" + (orderNo) + "行商品SKU编号不能为空！");
            }

            if (product.getPurchasePrice() == null) {
                throw new InputErrorException("第" + (orderNo) + "行商品采购价不能为空！");
            }

            if (product.getSalePrice() == null) {
                throw new InputErrorException("第" + (orderNo) + "行商品销售价不能为空！");
            }

            if (product.getRetailPrice() == null) {
                throw new InputErrorException("第" + (orderNo) + "行商品零售价不能为空！");
            }

            orderNo++;
        }
    }

    @Data
    public static class PropertyVo implements BaseVo, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 属性ID
         */
        @NotBlank(message = "属性值ID不能为空！")
        private String id;

        /**
         * 属性值内容
         */
        private String text;
    }

    @Data
    public static class ProductVo implements BaseVo, Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 销售属性1ID
         * multipleSaleProp == true时生效
         */
        private String salePropItemId1;

        /**
         * 销售属性2ID
         * multipleSaleProp == true时生效
         */
        private String salePropItemId2;

        /**
         * 商品编号
         */
        private String code;

        /**
         * 商品名称
         */
        private String name;

        /**
         * 商品SKU编号
         */
        private String skuCode;

        /**
         * 外部编号
         */
        private String externalCode;

        /**
         * 规格
         */
        private String spec;

        /**
         * 单位
         */
        private String unit;

        /**
         * 采购价
         */
        private BigDecimal purchasePrice;

        /**
         * 销售价
         */
        private BigDecimal salePrice;

        /**
         * 零售价
         */
        private BigDecimal retailPrice;
    }
}
