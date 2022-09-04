package com.lframework.xingyun.sc.biz.service.stock.adjust;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.sc.facade.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.facade.entity.StockCostAdjustSheet;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.ApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.ApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.BatchApprovePassStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.BatchApproveRefuseStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.CreateStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.QueryStockCostAdjustSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.adjust.UpdateStockCostAdjustSheetVo;
import java.util.List;

/**
 * 库存成本调整单 Service
 *
 * @author zmj
 */
public interface IStockCostAdjustSheetService extends BaseMpService<StockCostAdjustSheet> {

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<StockCostAdjustSheet> query(Integer pageIndex, Integer pageSize,
      QueryStockCostAdjustSheetVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<StockCostAdjustSheet> query(QueryStockCostAdjustSheetVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  StockCostAdjustSheetFullDto getDetail(String id);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateStockCostAdjustSheetVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateStockCostAdjustSheetVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  void deleteById(String id);

  /**
   * 根据IDs删除
   *
   * @param ids
   */
  void deleteByIds(List<String> ids);

  /**
   * 审核通过
   *
   * @param vo
   */
  void approvePass(ApprovePassStockCostAdjustSheetVo vo);

  /**
   * 批量审核通过
   *
   * @param vo
   */
  void batchApprovePass(BatchApprovePassStockCostAdjustSheetVo vo);

  /**
   * 直接审核通过
   *
   * @param vo
   */
  String directApprovePass(CreateStockCostAdjustSheetVo vo);

  /**
   * 审核拒绝
   *
   * @param vo
   */
  void approveRefuse(ApproveRefuseStockCostAdjustSheetVo vo);

  /**
   * 批量审核拒绝
   *
   * @param vo
   */
  void batchApproveRefuse(BatchApproveRefuseStockCostAdjustSheetVo vo);
}
