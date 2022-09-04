package com.lframework.xingyun.basedata.api.controller.product;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.FileUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ExcelUtil;
import com.lframework.starter.web.utils.UploadUtil;
import com.lframework.xingyun.basedata.api.bo.product.brand.GetProductBrandBo;
import com.lframework.xingyun.basedata.api.bo.product.brand.QueryProductBrandBo;
import com.lframework.xingyun.basedata.api.excel.product.brand.ProductBrandImportListener;
import com.lframework.xingyun.basedata.api.excel.product.brand.ProductBrandImportModel;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.UpdateProductBrandVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 品牌管理
 *
 * @author zmj
 */
@Api(tags = "品牌管理")
@Validated
@RestController
@RequestMapping("/basedata/product/brand")
public class ProductBrandController extends DefaultBaseController {

  @Autowired
  private IProductBrandService productBrandService;

  /**
   * 上传logo
   */
  @ApiOperation("上传logo")
  @ApiImplicitParam(value = "文件", name = "file", paramType = "form", required = true)
  @PostMapping("/upload/logo")
  public InvokeResult<String> uploadLogo(MultipartFile file) {

    if (!FileUtil.IMG_SUFFIX.contains(FileUtil.getSuffix(file.getOriginalFilename()))) {
      throw new DefaultClientException(
          "Logo图片仅支持【" + CollectionUtil.join(FileUtil.IMG_SUFFIX, StringPool.STR_SPLIT_CN)
              + "】格式！");
    }

    if (file.getSize() > 1 << 20) {
      throw new DefaultClientException("Logo图片大小不允许超过1MB");

    }
    String url = UploadUtil.upload(file);

    return InvokeResultBuilder.success(url);
  }

  /**
   * 品牌列表
   */
  @ApiOperation("品牌列表")
  @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryProductBrandBo>> query(@Valid QueryProductBrandVo vo) {

    PageResult<ProductBrand> pageResult = productBrandService.query(getPageIndex(vo),
        getPageSize(vo), vo);

    List<ProductBrand> datas = pageResult.getDatas();
    List<QueryProductBrandBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {

      results = datas.stream().map(QueryProductBrandBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }


  /**
   * 查询品牌
   */
  @ApiOperation("查询品牌")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('base-data:product:brand:query','base-data:product:brand:add','base-data:product:brand:modify')")
  @GetMapping
  public InvokeResult<GetProductBrandBo> get(@NotBlank(message = "ID不能为空！") String id) {

    ProductBrand data = productBrandService.findById(id);
    if (data == null) {
      throw new DefaultClientException("品牌不存在！");
    }

    GetProductBrandBo result = new GetProductBrandBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 批量停用品牌
   */
  @ApiOperation("批量停用品牌")
  @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要停用的品牌！") @RequestBody List<String> ids) {

    productBrandService.batchUnable(ids);

    for (String id : ids) {
      productBrandService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 批量启用品牌
   */
  @ApiOperation("批量启用品牌")
  @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "请选择需要启用的品牌！") @RequestBody List<String> ids) {

    productBrandService.batchEnable(ids);

    for (String id : ids) {
      productBrandService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 新增品牌
   */
  @ApiOperation("新增品牌")
  @PreAuthorize("@permission.valid('base-data:product:brand:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateProductBrandVo vo) {

    productBrandService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改品牌
   */
  @ApiOperation("修改品牌")
  @PreAuthorize("@permission.valid('base-data:product:brand:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateProductBrandVo vo) {

    productBrandService.update(vo);

    productBrandService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("下载导入模板")
  @PreAuthorize("@permission.valid('base-data:product:brand:import')")
  @GetMapping("/import/template")
  public void downloadImportTemplate() {
    ExcelUtil.exportXls("品牌导入模板", ProductBrandImportModel.class);
  }

  @ApiOperation("导入")
  @PreAuthorize("@permission.valid('base-data:product:brand:import')")
  @PostMapping("/import")
  public InvokeResult<Void> importExcel(@NotBlank(message = "ID不能为空") String id,
      @NotNull(message = "请上传文件") MultipartFile file) {

    ProductBrandImportListener listener = new ProductBrandImportListener();
    listener.setTaskId(id);
    ExcelUtil.read(file, ProductBrandImportModel.class, listener).sheet().doRead();

    return InvokeResultBuilder.success();
  }
}
