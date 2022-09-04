package com.lframework.xingyun.settle.biz.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.core.enums.SettleStatus;
import com.lframework.xingyun.settle.facade.dto.fee.customer.CustomerSettleFeeSheetFullDto;
import com.lframework.xingyun.settle.facade.entity.CustomerSettleFeeSheet;
import com.lframework.xingyun.settle.facade.vo.fee.customer.QueryCustomerSettleFeeSheetVo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-11-30
 */
public interface CustomerSettleFeeSheetMapper extends BaseMapper<CustomerSettleFeeSheet> {

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleFeeSheet> query(@Param("vo") QueryCustomerSettleFeeSheetVo vo);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    CustomerSettleFeeSheetFullDto getDetail(String id);

    /**
     * 查询列表
     *
     * @param vo
     * @return
     */
    List<CustomerSettleFeeSheetFullDto> queryFulls(@Param("vo") QueryCustomerSettleFeeSheetVo vo);

    /**
     * 查询已审核列表
     *
     * @param customerId
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomerSettleFeeSheet> getApprovedList(@Param("customerId") String customerId,
        @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime,
        @Param("settleStatus") SettleStatus settleStatus);
}
