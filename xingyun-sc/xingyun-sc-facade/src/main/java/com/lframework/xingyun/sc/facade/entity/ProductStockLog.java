package com.lframework.xingyun.sc.facade.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.sc.facade.enums.ProductStockBizType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_product_stock_log")
public class ProductStockLog extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 仓库ID
   */
  private String scId;

  /**
   * 商品ID
   */
  private String productId;

  /**
   * 批次ID
   */
  private String lotId;

  /**
   * 原库存数量
   */
  private Integer oriStockNum;

  /**
   * 现库存数量
   */
  private Integer curStockNum;

  /**
   * 原含税成本价
   */
  private BigDecimal oriTaxPrice;

  /**
   * 现含税成本价
   */
  private BigDecimal curTaxPrice;

  /**
   * 原无税成本价
   */
  private BigDecimal oriUnTaxPrice;

  /**
   * 现无税成本价
   */
  private BigDecimal curUnTaxPrice;

  /**
   * 变动库存数量
   */
  private Integer stockNum;

  /**
   * 变动含税金额
   */
  private BigDecimal taxAmount;

  /**
   * 变动无税金额
   */
  private BigDecimal unTaxAmount;

  /**
   * 创建人
   */
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 业务单据ID
   */
  private String bizId;

  /**
   * 业务单据号
   */
  private String bizCode;

  /**
   * 业务单据明细ID
   */
  private String bizDetailId;

  /**
   * 业务类型
   */
  private ProductStockBizType bizType;


}
