package com.lframework.xingyun.basedata.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.basedata.dto.product.retail.ProductRetailDto;
import com.lframework.xingyun.basedata.entity.ProductRetail;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-11
 */
public interface ProductRetailMapper extends BaseMapper<ProductRetail> {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductRetailDto getById(String id);
}
