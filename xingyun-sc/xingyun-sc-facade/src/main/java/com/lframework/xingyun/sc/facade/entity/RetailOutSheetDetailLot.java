package com.lframework.xingyun.sc.facade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.xingyun.core.enums.SettleStatus;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_retail_out_sheet_detail_lot")
public class RetailOutSheetDetailLot extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 明细ID
   */
  private String detailId;

  /**
   * 批次ID
   */
  private String lotId;

  /**
   * 出库数量
   */
  private Integer orderNum;

  /**
   * 已退货数量
   */
  private Integer returnNum;

  /**
   * 含税成本金额
   */
  private BigDecimal costTaxAmount;

  /**
   * 无税成本金额
   */
  private BigDecimal costUnTaxAmount;

  /**
   * 结算状态
   */
  private SettleStatus settleStatus;

  /**
   * 排序编号
   */
  private Integer orderNo;


}
