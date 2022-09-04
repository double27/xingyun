package com.lframework.xingyun.sc.facade.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-09-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tbl_retail_config")
public class RetailConfig extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "RetailConfig";
  private static final long serialVersionUID = 1L;
  /**
   * ID
   */
  private String id;

  /**
   * 零售出库单上的会员是否必填
   */
  private Boolean retailOutSheetRequireMember;

  /**
   * 零售退货单是否关联零售出库单
   */
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  private Boolean retailReturnMultipleRelateOutStock;

  /**
   * 零售退货单上的会员是否必填
   */
  private Boolean retailReturnRequireMember;
}
