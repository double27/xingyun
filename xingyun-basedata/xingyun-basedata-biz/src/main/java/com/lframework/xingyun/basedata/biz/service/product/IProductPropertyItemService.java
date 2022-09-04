package com.lframework.xingyun.basedata.biz.service.product;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.ProductPropertyItem;
import com.lframework.xingyun.basedata.facade.vo.product.property.item.CreateProductPropertyItemVo;
import com.lframework.xingyun.basedata.facade.vo.product.property.item.QueryProductPropertyItemVo;
import com.lframework.xingyun.basedata.facade.vo.product.property.item.UpdateProductPropertyItemVo;
import java.util.List;

public interface IProductPropertyItemService extends BaseMpService<ProductPropertyItem> {

    /**
     * 查询列表
     *
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductPropertyItem> query(Integer pageIndex, Integer pageSize, QueryProductPropertyItemVo vo);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<ProductPropertyItem> query(QueryProductPropertyItemVo vo);

    /**
     * 根据属性ID查询
     *
     * @param propertyId
     * @return
     */
    List<ProductPropertyItem> getByPropertyId(String propertyId);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    ProductPropertyItem findById(String id);

    /**
     * 新增
     *
     * @param vo
     * @return
     */
    String create(CreateProductPropertyItemVo vo);

    /**
     * 修改
     *
     * @param vo
     */
    void update(UpdateProductPropertyItemVo vo);
}
