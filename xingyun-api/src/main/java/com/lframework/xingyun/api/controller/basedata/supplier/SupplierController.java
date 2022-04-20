package com.lframework.xingyun.api.controller.basedata.supplier;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.basedata.supplier.GetSupplierBo;
import com.lframework.xingyun.api.bo.basedata.supplier.QuerySupplierBo;
import com.lframework.xingyun.basedata.entity.Supplier;
import com.lframework.xingyun.basedata.service.supplier.ISupplierService;
import com.lframework.xingyun.basedata.vo.supplier.CreateSupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.QuerySupplierVo;
import com.lframework.xingyun.basedata.vo.supplier.UpdateSupplierVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商管理
 *
 * @author zmj
 */
@Api(tags = "供应商管理")
@Validated
@RestController
@RequestMapping("/basedata/supplier")
public class SupplierController extends DefaultBaseController {

    @Autowired
    private ISupplierService supplierService;

    /**
     * 供应商列表
     */
    @ApiOperation("供应商列表")
    @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
    @GetMapping("/query")
    public InvokeResult<PageResult<QuerySupplierBo>> query(@Valid QuerySupplierVo vo) {

        PageResult<Supplier> pageResult = supplierService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<Supplier> datas = pageResult.getDatas();
        List<QuerySupplierBo> results = null;

        if (!CollectionUtil.isEmpty(datas)) {

            results = datas.stream().map(QuerySupplierBo::new).collect(Collectors.toList());
        }

        return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
    }

    /**
     * 查询供应商
     */
    @ApiOperation("查询供应商")
    @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
    @PreAuthorize("@permission.valid('base-data:supplier:query','base-data:supplier:add','base-data:supplier:modify')")
    @GetMapping
    public InvokeResult<GetSupplierBo> get(@NotBlank(message = "ID不能为空！") String id) {

        Supplier data = supplierService.findById(id);
        if (data == null) {
            throw new DefaultClientException("供应商不存在！");
        }

        GetSupplierBo result = new GetSupplierBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用供应商
     */
    @ApiOperation("批量停用供应商")
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult<Void> batchUnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的供应商！") @RequestBody List<String> ids) {

        supplierService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用供应商
     */
    @ApiOperation("批量启用供应商")
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult<Void> batchEnable(
            @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的供应商！") @RequestBody List<String> ids) {

        supplierService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增供应商
     */
    @ApiOperation("新增供应商")
    @PreAuthorize("@permission.valid('base-data:supplier:add')")
    @PostMapping
    public InvokeResult<Void> create(@Valid CreateSupplierVo vo) {

        supplierService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改供应商
     */
    @ApiOperation("修改供应商")
    @PreAuthorize("@permission.valid('base-data:supplier:modify')")
    @PutMapping
    public InvokeResult<Void> update(@Valid UpdateSupplierVo vo) {

        supplierService.update(vo);

        return InvokeResultBuilder.success();
    }
}
