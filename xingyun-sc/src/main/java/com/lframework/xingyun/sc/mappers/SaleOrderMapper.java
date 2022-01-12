package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.SaleOrderDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderFullDto;
import com.lframework.xingyun.sc.dto.sale.SaleOrderWithOutDto;
import com.lframework.xingyun.sc.entity.SaleOrder;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderVo;
import com.lframework.xingyun.sc.vo.sale.QuerySaleOrderWithOutVo;
import com.lframework.xingyun.sc.vo.sale.SaleOrderSelectorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-21
 */
public interface SaleOrderMapper extends BaseMapper<SaleOrder> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOrderDto> query(@Param("vo") QuerySaleOrderVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOrderDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOrderFullDto getDetail(String id);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOrderFullDto> queryFulls(@Param("vo") QuerySaleOrderVo vo);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<SaleOrderDto> selector(@Param("vo") SaleOrderSelectorVo vo);

    /**
     * 根据ID查询（出库业务）
     * @param id
     * @return
     */
    SaleOrderWithOutDto getWithOut(@Param("id") String id, @Param("requireSale") Boolean requireSale);

    /**
     * 查询列表（出库业务）
     * @param vo
     * @return
     */
    List<SaleOrderDto> queryWithOut(@Param("vo") QuerySaleOrderWithOutVo vo,
            @Param("multipleRelate") boolean multipleRelate);
}
