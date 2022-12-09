package com.lframework.xingyun.basedata.api.bo.product.saleprop;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSalePropGroupSelectorBo extends BaseBo<ProductSalePropGroup> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  public ProductSalePropGroupSelectorBo() {

  }

  public ProductSalePropGroupSelectorBo(ProductSalePropGroup dto) {

    super(dto);
  }
}