package com.lframework.xingyun.sc.facade.vo.stock;

import com.lframework.starter.web.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryProductStockVo extends PageVo {

  /**
   * 仓库ID
   */
  @ApiModelProperty("仓库ID")
  private String scId;

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
   * 商品类目ID
   */
  @ApiModelProperty("商品类目ID")
  private String categoryId;

  /**
   * 商品品牌ID
   */
  @ApiModelProperty("商品品牌ID")
  private String brandId;
}
