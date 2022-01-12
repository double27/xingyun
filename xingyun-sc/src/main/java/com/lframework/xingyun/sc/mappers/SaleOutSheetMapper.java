package com.lframework.xingyun.sc.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetFullDto;
import com.lframework.xingyun.sc.dto.sale.out.SaleOutSheetWithReturnDto;
import com.lframework.xingyun.sc.entity.SaleOutSheet;
import com.lframework.xingyun.sc.vo.sale.out.QuerySaleOutSheetVo;
import com.lframework.xingyun.sc.vo.sale.out.QuerySaleOutSheetWithReturnVo;
import com.lframework.xingyun.sc.vo.sale.out.SaleOutSheetSelectorVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-10-26
 */
public interface SaleOutSheetMapper extends BaseMapper<SaleOutSheet> {

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOutSheetDto> query(@Param("vo") QuerySaleOutSheetVo vo);

    /**
     * 选择器
     * @param vo
     * @return
     */
    List<SaleOutSheetDto> selector(@Param("vo") SaleOutSheetSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetDto getById(String id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    SaleOutSheetFullDto getDetail(String id);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<SaleOutSheetFullDto> queryFulls(@Param("vo") QuerySaleOutSheetVo vo);

    /**
     * 根据ID查询（销售退货业务）
     * @param id
     * @return
     */
    SaleOutSheetWithReturnDto getWithReturn(@Param("id") String id, @Param("requireOut") Boolean requireOut);

    /**
     * 查询列表（销售退货业务）
     * @param vo
     * @return
     */
    List<SaleOutSheetDto> queryWithReturn(@Param("vo") QuerySaleOutSheetWithReturnVo vo,
            @Param("multipleRelate") boolean multipleRelate);
}
