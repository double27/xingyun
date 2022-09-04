package com.lframework.xingyun.sc.api.bo.stock.take.pre;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetSelectorDto;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PreTakeStockSheetSelectorBo extends BaseBo<PreTakeStockSheetSelectorDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 业务单据号
   */
  @ApiModelProperty("业务单据号")
  private String code;

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
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  private Integer takeStatus;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public PreTakeStockSheetSelectorBo(PreTakeStockSheetSelectorDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<PreTakeStockSheetSelectorDto> convert(PreTakeStockSheetSelectorDto dto) {

    return super.convert(dto, PreTakeStockSheetSelectorBo::getTakeStatus);
  }

  @Override
  protected void afterInit(PreTakeStockSheetSelectorDto dto) {

    this.takeStatus = dto.getTakeStatus().getCode();

    StoreCenterFeignClient storeCenterService = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId()).getData();
    this.scCode = sc.getCode();
    this.scName = sc.getName();
  }
}
