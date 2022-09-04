package com.lframework.xingyun.basedata.api.bo.product.property;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryPropertyService;
import com.lframework.xingyun.basedata.biz.service.product.IProductCategoryService;
import com.lframework.xingyun.basedata.facade.entity.ProductCategory;
import com.lframework.xingyun.basedata.facade.entity.ProductCategoryProperty;
import com.lframework.xingyun.basedata.facade.entity.ProductProperty;
import com.lframework.xingyun.basedata.facade.enums.PropertyType;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetProductPropertyBo extends BaseBo<ProductProperty> {

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
   * 是否必填
   */
  @ApiModelProperty("是否必填")
  private Boolean isRequired;

  /**
   * 录入类型
   */
  @ApiModelProperty("录入类型")
  private Integer columnType;

  /**
   * 数据类型
   */
  @ApiModelProperty("数据类型")
  private Integer columnDataType;

  /**
   * 属性类别
   */
  @ApiModelProperty("属性类别")
  private Integer propertyType;

  /**
   * 类目
   */
  @ApiModelProperty("类目")
  private List<CategoryBo> categories;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class CategoryBo extends BaseBo<ProductCategory> {

    /**
     * 类目ID
     */
    @ApiModelProperty("类目ID")
    private String id;

    /**
     * 类目名称
     */
    @ApiModelProperty("类目名称")
    private String name;

    public CategoryBo() {

    }

    public CategoryBo(ProductCategory dto) {

      super(dto);
    }
  }

  public GetProductPropertyBo() {

  }

  public GetProductPropertyBo(ProductProperty dto) {

    super(dto);
  }

  @Override
  protected void afterInit(ProductProperty dto) {

    this.columnType = dto.getColumnType().getCode();
    this.propertyType = dto.getPropertyType().getCode();
    if (dto.getColumnDataType() != null) {
      this.columnDataType = dto.getColumnDataType().getCode();
    }

    if (dto.getPropertyType() == PropertyType.APPOINT) {
      IProductCategoryPropertyService productCategoryPropertyService = ApplicationUtil.getBean(
          IProductCategoryPropertyService.class);
      List<ProductCategoryProperty> categoryPropertyList = productCategoryPropertyService.getByPropertyId(
          dto.getId());

      IProductCategoryService productCategoryService = ApplicationUtil.getBean(
          IProductCategoryService.class);
      this.categories = categoryPropertyList.stream()
          .map(t -> productCategoryService.findById(t.getCategoryId()))
          .map(CategoryBo::new).collect(Collectors.toList());
    }
  }
}
