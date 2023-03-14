package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustProductDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.entity.StockCostAdjustSheet;
import com.lframework.xingyun.sc.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 库存成本调整单 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface StockCostAdjustSheetMapper extends BaseMapper<StockCostAdjustSheet> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockCostAdjustSheet> query(@Param("vo") QueryStockCostAdjustSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCostAdjustSheetFullDto getDetail(@Param("id") String id);

  /**
   * 根据关键字查询库存成本调整单商品信息
   *
   * @param scId
   * @param condition
   * @return
   */
  List<StockCostAdjustProductDto> queryStockCostAdjustByCondition(
      @Param("scId") String scId, @Param("condition") String condition);

  /**
   * 查询库存成本调整单商品信息
   *
   * @param vo
   * @return
   */
  List<StockCostAdjustProductDto> queryStockCostAdjustList(@Param("vo") QueryStockCostAdjustProductVo vo);
}
