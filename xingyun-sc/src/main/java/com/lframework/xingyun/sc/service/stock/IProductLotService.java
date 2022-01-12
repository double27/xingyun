package com.lframework.xingyun.sc.service.stock;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.stock.ProductLotDto;
import com.lframework.xingyun.sc.dto.stock.ProductLotWithStockDto;
import com.lframework.xingyun.sc.vo.stock.lot.CreateProductLotVo;
import com.lframework.xingyun.sc.vo.stock.lot.QueryProductLotVo;

import java.util.List;

public interface IProductLotService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<ProductLotWithStockDto> query(Integer pageIndex, Integer pageSize, QueryProductLotVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<ProductLotWithStockDto> query(QueryProductLotVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductLotDto getById(String id);

    /**
     * 创建
     * @param vo
     */
    String create(CreateProductLotVo vo);
}
