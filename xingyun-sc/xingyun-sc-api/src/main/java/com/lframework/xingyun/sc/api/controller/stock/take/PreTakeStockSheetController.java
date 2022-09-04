package com.lframework.xingyun.sc.api.controller.stock.take;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.sc.api.bo.stock.take.pre.GetPreTakeStockSheetBo;
import com.lframework.xingyun.sc.api.bo.stock.take.pre.QueryPreTakeStockSheetBo;
import com.lframework.xingyun.sc.api.bo.stock.take.pre.QueryPreTakeStockSheetProductBo;
import com.lframework.xingyun.sc.api.excel.stock.take.pre.PreTakeStockSheetExportModel;
import com.lframework.xingyun.sc.biz.service.stock.take.IPreTakeStockSheetService;
import com.lframework.xingyun.sc.biz.service.stock.take.ITakeStockPlanService;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.PreTakeStockSheetFullDto;
import com.lframework.xingyun.sc.facade.dto.stock.take.pre.QueryPreTakeStockSheetProductDto;
import com.lframework.xingyun.sc.facade.entity.PreTakeStockSheet;
import com.lframework.xingyun.sc.facade.entity.TakeStockPlan;
import com.lframework.xingyun.sc.facade.enums.TakeStockPlanType;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.CreatePreTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.QueryPreTakeStockSheetVo;
import com.lframework.xingyun.sc.facade.vo.stock.take.pre.UpdatePreTakeStockSheetVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预先盘点单 Controller
 *
 * @author zmj
 */
@Api(tags = "预先盘点单")
@Validated
@RestController
@RequestMapping("/stock/take/pre")
public class PreTakeStockSheetController extends DefaultBaseController {

  @Autowired
  private IPreTakeStockSheetService preTakeStockSheetService;

  @Autowired
  private ITakeStockPlanService takeStockPlanService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('stock:take:pre:query')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryPreTakeStockSheetBo>> query(
      @Valid QueryPreTakeStockSheetVo vo) {

    PageResult<PreTakeStockSheet> pageResult = preTakeStockSheetService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);

    List<PreTakeStockSheet> datas = pageResult.getDatas();
    List<QueryPreTakeStockSheetBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryPreTakeStockSheetBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 导出列表
   */
  @ApiOperation("导出列表")
  @PreAuthorize("@permission.valid('stock:take:pre:export')")
  @PostMapping("/export")
  public void export(@Valid QueryPreTakeStockSheetVo vo) {

    ExcelMultipartWriterSheetBuilder builder = ExcelUtil.multipartExportXls("预先盘点单信息",
        PreTakeStockSheetExportModel.class);

    try {
      int pageIndex = 1;
      while (true) {
        PageResult<PreTakeStockSheet> pageResult = preTakeStockSheetService.query(pageIndex,
            getExportSize(),
            vo);
        List<PreTakeStockSheet> datas = pageResult.getDatas();
        List<PreTakeStockSheetExportModel> models = datas.stream()
            .map(PreTakeStockSheetExportModel::new)
            .collect(Collectors.toList());
        builder.doWrite(models);

        if (!pageResult.isHasNext()) {
          break;
        }
        pageIndex++;
      }
    } finally {
      builder.finish();
    }
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('stock:take:pre:query')")
  @GetMapping
  public InvokeResult<GetPreTakeStockSheetBo> getDetail(@NotBlank(message = "id不能为空！") String id) {

    PreTakeStockSheetFullDto data = preTakeStockSheetService.getDetail(id);
    if (data == null) {
      throw new DefaultClientException("预先盘点单不存在！");
    }

    GetPreTakeStockSheetBo result = new GetPreTakeStockSheetBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据预先盘点单、盘点任务查询商品信息
   */
  @ApiOperation("根据预先盘点单、盘点任务查询商品信息")
  @ApiImplicitParams({
      @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true),
      @ApiImplicitParam(value = "盘点任务ID", name = "planId", paramType = "query", required = true)})
  @PreAuthorize("@permission.valid('stock:take:sheet:add', 'stock:take:sheet:modify')")
  @GetMapping("/products")
  public InvokeResult<List<QueryPreTakeStockSheetProductBo>> getProducts(
      @NotBlank(message = "ID不能为空！") String id,
      @NotBlank(message = "盘点任务ID不能为空！") String planId) {

    TakeStockPlan takeStockPlan = takeStockPlanService.getById(planId);
    if (takeStockPlan.getTakeType() == TakeStockPlanType.SIMPLE) {
      planId = null;
    }

    List<QueryPreTakeStockSheetProductDto> datas = preTakeStockSheetService.getProducts(id, planId);
    List<QueryPreTakeStockSheetProductBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream()
          .map(t -> new QueryPreTakeStockSheetProductBo(t, takeStockPlan.getScId()))
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增
   */
  @ApiOperation("新增")
  @PreAuthorize("@permission.valid('stock:take:pre:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreatePreTakeStockSheetVo vo) {

    vo.validate();

    preTakeStockSheetService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('stock:take:pre:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdatePreTakeStockSheetVo vo) {

    vo.validate();

    preTakeStockSheetService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('stock:take:pre:delete')")
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {

    preTakeStockSheetService.deleteById(id);

    return InvokeResultBuilder.success();
  }

  /**
   * 批量删除
   */
  @PreAuthorize("@permission.valid('stock:take:pre:delete')")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择要执行操作的预先盘点单！") @RequestBody List<String> ids) {

    preTakeStockSheetService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }
}
