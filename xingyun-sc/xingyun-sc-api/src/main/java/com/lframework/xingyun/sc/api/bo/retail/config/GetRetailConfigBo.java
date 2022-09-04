package com.lframework.xingyun.sc.api.bo.retail.config;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.sc.facade.entity.RetailConfig;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetRetailConfigBo extends BaseBo<RetailConfig> {

  /**
   * 零售出库单上的会员是否必填
   */
  @ApiModelProperty("零售出库单上的会员是否必填")
  private Boolean retailOutSheetRequireMember;

  /**
   * 零售退货单上的会员是否必填
   */
  @ApiModelProperty("零售退货单上的会员是否必填")
  private Boolean retailReturnRequireMember;

  /**
   * 零售退货单是否关联零售出库单
   */
  @ApiModelProperty("零售退货单是否关联零售出库单")
  private Boolean retailReturnRequireOutStock;

  /**
   * 零售退货单是否多次关联零售出库单
   */
  @ApiModelProperty("零售退货单是否多次关联零售出库单")
  private Boolean retailReturnMultipleRelateOutStock;

  public GetRetailConfigBo() {

  }

  public GetRetailConfigBo(RetailConfig dto) {

    super(dto);
  }
}
