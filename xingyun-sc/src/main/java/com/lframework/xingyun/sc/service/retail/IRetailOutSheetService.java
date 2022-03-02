package com.lframework.xingyun.sc.service.retail;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.sc.dto.purchase.receive.GetPaymentDateDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetFullDto;
import com.lframework.xingyun.sc.dto.retail.out.RetailOutSheetWithReturnDto;
import com.lframework.xingyun.sc.vo.retail.out.*;

import java.util.List;

public interface IRetailOutSheetService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<RetailOutSheetDto> query(Integer pageIndex, Integer pageSize, QueryRetailOutSheetVo vo);

    /**
     * 查询列表
     * @param vo
     * @return
     */
    List<RetailOutSheetDto> query(QueryRetailOutSheetVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<RetailOutSheetDto> selector(Integer pageIndex, Integer pageSize, RetailOutSheetSelectorVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RetailOutSheetDto getById(String id);

    /**
     * 根据会员ID查询默认付款日期
     * @param memberId
     */
    GetPaymentDateDto getPaymentDate(String memberId);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    RetailOutSheetFullDto getDetail(String id);

    /**
     * 根据ID查询（零售退货业务）
     * @param id
     * @return
     */
    RetailOutSheetWithReturnDto getWithReturn(String id);

    /**
     * 查询列表（零售退货业务）
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<RetailOutSheetDto> queryWithReturn(Integer pageIndex, Integer pageSize,
            QueryRetailOutSheetWithReturnVo vo);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateRetailOutSheetVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateRetailOutSheetVo vo);

    /**
     * 审核通过
     * @param vo
     */
    void approvePass(ApprovePassRetailOutSheetVo vo);

    /**
     * 批量审核通过
     * @param vo
     */
    void batchApprovePass(BatchApprovePassRetailOutSheetVo vo);

    /**
     * 直接审核通过
     * @param vo
     */
    void directApprovePass(CreateRetailOutSheetVo vo);

    /**
     * 审核拒绝
     * @param vo
     */
    void approveRefuse(ApproveRefuseRetailOutSheetVo vo);

    /**
     * 批量审核拒绝
     * @param vo
     */
    void batchApproveRefuse(BatchApproveRefuseRetailOutSheetVo vo);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据IDs删除
     * @param ids
     */
    void deleteByIds(List<String> ids);
}
