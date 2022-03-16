package com.lframework.xingyun.api.controller.stock.adjust;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.xingyun.api.bo.stock.adjust.QueryStockCostAdjustSheetBo;
import com.lframework.xingyun.api.bo.stock.adjust.StockCostAdjustProductBo;
import com.lframework.xingyun.api.bo.stock.adjust.StockCostAdjustSheetFullBo;
import com.lframework.xingyun.api.model.stock.adjust.StockCostAdjustSheetExportModel;
import com.lframework.xingyun.basedata.dto.product.info.StockCostAdjustProductDto;
import com.lframework.xingyun.basedata.service.product.IProductService;
import com.lframework.xingyun.basedata.vo.product.info.QueryStockCostAdjustProductVo;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetDto;
import com.lframework.xingyun.sc.dto.stock.adjust.StockCostAdjustSheetFullDto;
import com.lframework.xingyun.sc.service.stock.adjust.IStockCostAdjustSheetService;
import com.lframework.xingyun.sc.vo.stock.adjust.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存成本调整单 Controller
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/stock/adjust/cost")
public class StockCostAdjustSheetController extends DefaultBaseController {

    @Autowired
    private IStockCostAdjustSheetService stockCostAdjustSheetService;

    @Autowired
    private IProductService productService;

    /**
     * 查询列表
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:query')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QueryStockCostAdjustSheetVo vo) {

        PageResult<StockCostAdjustSheetDto> pageResult = stockCostAdjustSheetService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<StockCostAdjustSheetDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QueryStockCostAdjustSheetBo> results = datas.stream().map(QueryStockCostAdjustSheetBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 根据ID查询
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:query')")
    @GetMapping("/detail")
    public InvokeResult getDetail(@NotBlank(message = "id不能为空！") String id) {

        StockCostAdjustSheetFullDto data = stockCostAdjustSheetService.getDetail(id);
        if (data == null) {
            throw new DefaultClientException("库存成本调整单不存在！");
        }

        StockCostAdjustSheetFullBo result = new StockCostAdjustSheetFullBo(data);

        return InvokeResultBuilder.success(result);
    }

    @PreAuthorize("@permission.valid('stock:adjust:cost:export')")
    @PostMapping("/export")
    public void export(@Valid QueryStockCostAdjustSheetVo vo) {

        ExcelMultipartWriterSheetBuilder builder = ExcelUtil
                .multipartExportXls("库存成本调整单信息", StockCostAdjustSheetExportModel.class);

        try {
            int pageIndex = 1;
            while (true) {
                PageResult<StockCostAdjustSheetDto> pageResult = stockCostAdjustSheetService.query(pageIndex, getExportSize(), vo);
                List<StockCostAdjustSheetDto> datas = pageResult.getDatas();
                List<StockCostAdjustSheetExportModel> models = datas.stream().map(StockCostAdjustSheetExportModel::new)
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
     * 根据关键字查询商品列表
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
    @GetMapping("/product/search")
    public InvokeResult searchProducts(@NotBlank(message = "仓库ID不能为空！") String scId, String condition) {
        if (StringUtil.isBlank(condition)) {
            return InvokeResultBuilder.success(Collections.EMPTY_LIST);
        }
        PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustByCondition(getPageIndex(), getPageSize(), scId, condition);
        List<StockCostAdjustProductBo> results = Collections.EMPTY_LIST;
        List<StockCostAdjustProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询商品列表
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:add', 'stock:adjust:cost:modify')")
    @GetMapping("/product/list")
    public InvokeResult queryProductList(@Valid QueryStockCostAdjustProductVo vo) {

        PageResult<StockCostAdjustProductDto> pageResult = productService.queryStockCostAdjustList(getPageIndex(), getPageSize(), vo);
        List<StockCostAdjustProductBo> results = Collections.EMPTY_LIST;
        List<StockCostAdjustProductDto> datas = pageResult.getDatas();
        if (!CollectionUtil.isEmpty(datas)) {
            results = datas.stream().map(StockCostAdjustProductBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 新增
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:add')")
    @PostMapping
    public InvokeResult create(@Valid @RequestBody CreateStockCostAdjustSheetVo vo) {

        vo.validate();

        stockCostAdjustSheetService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:modify')")
    @PutMapping
    public InvokeResult update(@Valid @RequestBody UpdateStockCostAdjustSheetVo vo) {

        vo.validate();

        stockCostAdjustSheetService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 根据ID删除
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:delete')")
    @DeleteMapping
    public InvokeResult deleteById(@NotBlank(message = "id不能为空！") String id) {

        stockCostAdjustSheetService.deleteById(id);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量删除
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:delete')")
    @DeleteMapping("/batch")
    public InvokeResult deleteByIds(@RequestBody @NotEmpty(message = "请选择需要删除的库存成本调整单！") List<String> ids) {

        stockCostAdjustSheetService.deleteByIds(ids);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核通过
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/pass")
    public InvokeResult approvePass(@RequestBody @Valid ApprovePassStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.approvePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核通过
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/pass/batch")
    public InvokeResult batchApprovePass(@RequestBody @Valid BatchApprovePassStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.batchApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 直接审核通过
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PostMapping("/approve/pass/direct")
    public InvokeResult directApprovePass(@RequestBody @Valid CreateStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.directApprovePass(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 审核拒绝
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/refuse")
    public InvokeResult approveRefuse(@RequestBody @Valid ApproveRefuseStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.approveRefuse(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 批量审核拒绝
     */
    @PreAuthorize("@permission.valid('stock:adjust:cost:approve')")
    @PatchMapping("/approve/refuse/batch")
    public InvokeResult batchApproveRefuse(@RequestBody @Valid BatchApproveRefuseStockCostAdjustSheetVo vo) {

        stockCostAdjustSheetService.batchApproveRefuse(vo);

        return InvokeResultBuilder.success();
    }
}