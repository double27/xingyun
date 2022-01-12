package com.lframework.xingyun.api.bo.chart;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.chart.dto.OrderChartSameMonthDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderChartSameMonthBo extends BaseBo<OrderChartSameMonthDto> {

    /**
     * 单据总金额
     */
    private BigDecimal totalAmount;

    /**
     * 单据总数量
     */
    private Integer totalNum;

    /**
     * 创建时间
     */
    private String createDate;

    public OrderChartSameMonthBo() {

    }

    public OrderChartSameMonthBo(OrderChartSameMonthDto dto) {

        super(dto);
    }
}
