package com.lframework.xingyun.sc.api.bo.stock.take.plan;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.facade.ProductBrandFeignClient;
import com.lframework.xingyun.basedata.facade.ProductCategoryFeignClient;
import com.lframework.xingyun.basedata.facade.StoreCenterFeignClient;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import com.lframework.xingyun.basedata.facade.entity.StoreCenter;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import com.lframework.xingyun.sc.facade.enums.TakeStockPlanType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 盘点任务 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GetTakeStockPlanBo extends BaseBo<TakeStockPlan> {

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
   * 盘点类别
   */
  @ApiModelProperty("盘点类别")
  private Integer takeType;

  /**
   * 盘点状态
   */
  @ApiModelProperty("盘点状态")
  private Integer takeStatus;

  /**
   * 业务名称
   */
  @ApiModelProperty("业务名称")
  private String bizName;

  public GetTakeStockPlanBo() {

  }

  public GetTakeStockPlanBo(TakeStockPlan dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<TakeStockPlan> convert(TakeStockPlan dto) {

    return super.convert(dto, GetTakeStockPlanBo::getTakeType, GetTakeStockPlanBo::getTakeStatus);
  }

  @Override
  protected void afterInit(TakeStockPlan dto) {

    this.takeType = dto.getTakeType().getCode();
    this.takeStatus = dto.getTakeStatus().getCode();

    StoreCenterFeignClient storeCenterService = ApplicationUtil.getBean(
        StoreCenterFeignClient.class);
    StoreCenter sc = storeCenterService.findById(dto.getScId()).getData();

    this.scName = sc.getName();

    String bizId = dto.getBizId();
    if (dto.getTakeType() == TakeStockPlanType.CATEGORY) {
      ProductCategoryFeignClient productCategoryService = ApplicationUtil.getBean(
          ProductCategoryFeignClient.class);
      String[] categoryIds = bizId.split(",");
      StringBuilder builder = new StringBuilder();
      for (String categoryId : categoryIds) {
        ProductCategory productCategory = productCategoryService.findById(categoryId).getData();
        builder.append(productCategory.getName()).append(StringPool.STR_SPLIT_CN);
      }

      if (builder.length() > 0) {
        builder.setLength(builder.length() - 1);
      }

      this.bizName = builder.toString();
    } else if (dto.getTakeType() == TakeStockPlanType.BRAND) {
      ProductBrandFeignClient productBrandFeignClient = ApplicationUtil.getBean(
          ProductBrandFeignClient.class);
      String[] brandIds = bizId.split(",");
      StringBuilder builder = new StringBuilder();
      for (String brandId : brandIds) {
        ProductBrand productBrand = productBrandFeignClient.findById(brandId).getData();
        builder.append(productBrand.getName()).append(StringPool.STR_SPLIT_CN);
      }

      if (builder.length() > 0) {
        builder.setLength(builder.length() - 1);
      }

      this.bizName = builder.toString();
    }
  }
}
