package com.lframework.xingyun.basedata.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.facade.entity.ProductSalePropItem;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.QueryProductSalePropItemSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.saleprop.item.QueryProductSalePropItemVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-13
 */
public interface ProductSalePropItemMapper extends BaseMapper<ProductSalePropItem> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<ProductSalePropItem> query(@Param("vo") QueryProductSalePropItemVo vo);

  /**
   * 选择器
   *
   * @param vo
   * @return
   */
  List<ProductSalePropItem> selector(@Param("vo") QueryProductSalePropItemSelectorVo vo);

  /**
   * 根据GroupId查询
   *
   * @param groupId
   * @return
   */
  List<ProductSalePropItem> getByGroupId(String groupId);

  /**
   * 根据销售属性组ID查询启用的销售属性
   *
   * @param groupId
   * @return
   */
  List<ProductSalePropItem> getEnablesByGroupId(String groupId);
}
