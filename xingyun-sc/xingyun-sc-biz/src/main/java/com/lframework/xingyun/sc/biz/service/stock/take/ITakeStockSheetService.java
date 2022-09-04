package com.lframework.xingyun.sc.biz.service.stock.take;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.stock.take.sheet.TakeStockSheetFullDto;
import com.lframework.xingyun.sc.facade.entity.TakeStockSheet;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.ApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.ApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.BatchApprovePassTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.BatchApproveRefuseTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.CreateTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.QueryTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.sheet.UpdateTakeStockSheetVo;
import java.util.List;

/**
 * 盘点单 Service
 *
 * @author zmj
 */
public interface ITakeStockSheetService extends BaseMpService<TakeStockSheet> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<TakeStockSheet> query(Integer pageIndex, Integer pageSize, QueryTakeStockSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<TakeStockSheet> query(QueryTakeStockSheetVo vo);

  /**
   * 根据ID查询详情
   *
   * @param id
   * @return
   */
  TakeStockSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateTakeStockSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateTakeStockSheetVo vo);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassTakeStockSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassTakeStockSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateTakeStockSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseTakeStockSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseTakeStockSheetVo vo);

  /**
   * 取消审核
   *
   * @param id
   */
  void cancelApprovePass(String id);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);

  /**
   * 根据ID删除
   *
   * @param ids
   */
  void batchDelete(List<String> ids);

  /**
   * 根据预先盘点单ID判断是否有盘点单关联
   *
   * @param preSheetId
   * @return
   */
  Boolean hasRelatePreTakeStockSheet(String preSheetId);

  /**
   * 根据盘点任务ID查询是否有未审核通过的盘点单
   *
   * @param planId
   * @return
   */
  Boolean hasUnApprove(String planId);
}
