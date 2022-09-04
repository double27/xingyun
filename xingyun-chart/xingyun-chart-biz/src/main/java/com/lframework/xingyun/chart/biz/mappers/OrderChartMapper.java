package com.lframework.xingyun.chart.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.chart.facade.dto.OrderChartSameMonthDto;
import com.lframework.xingyun.chart.facade.dto.OrderChartSumDto;
import com.lframework.xingyun.chart.facade.dto.OrderChartTodayDto;
import com.lframework.xingyun.chart.facade.entity.OrderChart;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-19
 */
public interface OrderChartMapper extends BaseMapper<OrderChart> {

  /**
   * 汇总数据
   *
   * @param bizTypes
   * @param startTime
   * @param endTime
   * @return
   */
  OrderChartSumDto getChartSum(@Param("bizTypes") List<Integer> bizTypes,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);

  /**
   * 查询列表
   *
   * @param bizTypes
   * @param startTime
   * @param endTime
   * @return
   */
  List<OrderChartSameMonthDto> querySameMonth(@Param("bizTypes") List<Integer> bizTypes,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

  /**
   * 查询当日数据列表
   *
   * @param bizTypes
   * @param startTime
   * @param endTime
   * @return
   */
  List<OrderChartTodayDto> queryToday(@Param("bizTypes") List<Integer> bizTypes,
      @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
