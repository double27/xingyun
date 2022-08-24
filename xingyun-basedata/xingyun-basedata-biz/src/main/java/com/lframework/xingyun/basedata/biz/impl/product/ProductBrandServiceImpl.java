package com.lframework.xingyun.basedata.biz.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.xingyun.basedata.biz.mappers.ProductBrandMapper;
import com.lframework.xingyun.basedata.biz.service.product.IProductBrandService;
import com.lframework.xingyun.basedata.facade.entity.ProductBrand;
import com.lframework.xingyun.basedata.facade.vo.product.brand.CreateProductBrandVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.QueryProductBrandSelectorVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.QueryProductBrandVo;
import com.lframework.xingyun.basedata.facade.vo.product.brand.UpdateProductBrandVo;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductBrandServiceImpl extends BaseMpServiceImpl<ProductBrandMapper, ProductBrand>
    implements IProductBrandService {

  @Override
  public PageResult<ProductBrand> query(Integer pageIndex, Integer pageSize,
      QueryProductBrandVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductBrand> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<ProductBrand> query(QueryProductBrandVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<ProductBrand> selector(Integer pageIndex, Integer pageSize,
      QueryProductBrandSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<ProductBrand> datas = getBaseMapper().selector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = ProductBrand.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public ProductBrand findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "停用商品品牌，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
        .set(ProductBrand::getAvailable, Boolean.FALSE).in(ProductBrand::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用商品品牌，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(Collection<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    Wrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
        .set(ProductBrand::getAvailable, Boolean.TRUE).in(ProductBrand::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateProductBrandVo vo) {

    Wrapper<ProductBrand> checkCodeWrapper = Wrappers.lambdaQuery(ProductBrand.class)
        .eq(ProductBrand::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
        .eq(ProductBrand::getName, vo.getName());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    ProductBrand data = new ProductBrand();
    data.setId(IdUtil.getId());
    data.setCode(vo.getCode());
    data.setName(vo.getName());
    data.setShortName(
        StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName());
    data.setIntroduction(
        StringUtil.isBlank(vo.getIntroduction()) ? StringPool.EMPTY_STR : vo.getIntroduction());
    data.setAvailable(Boolean.TRUE);
    data.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());
    if (!StringUtil.isBlank(vo.getLogo())) {
      data.setLogo(vo.getLogo());
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改商品品牌，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateProductBrandVo vo) {

    ProductBrand data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("品牌不存在！");
    }

    Wrapper<ProductBrand> checkWrapper = Wrappers.lambdaQuery(ProductBrand.class)
        .eq(ProductBrand::getCode, vo.getCode()).ne(ProductBrand::getId, vo.getId());
    if (getBaseMapper().selectCount(checkWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<ProductBrand> checkNameWrapper = Wrappers.lambdaQuery(ProductBrand.class)
        .eq(ProductBrand::getName, vo.getName()).ne(ProductBrand::getId, vo.getId());
    if (getBaseMapper().selectCount(checkNameWrapper) > 0) {
      throw new DefaultClientException("名称重复，请重新输入！");
    }

    LambdaUpdateWrapper<ProductBrand> updateWrapper = Wrappers.lambdaUpdate(ProductBrand.class)
        .set(ProductBrand::getCode, vo.getCode()).set(ProductBrand::getName, vo.getName())
        .set(ProductBrand::getShortName,
            StringUtil.isBlank(vo.getShortName()) ? StringPool.EMPTY_STR : vo.getShortName())
        .set(ProductBrand::getLogo, StringUtil.isBlank(vo.getLogo()) ? null : vo.getLogo())
        .set(ProductBrand::getIntroduction,
            StringUtil.isBlank(vo.getIntroduction()) ? StringPool.EMPTY_STR : vo.getIntroduction())
        .set(ProductBrand::getAvailable, vo.getAvailable()).set(ProductBrand::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(ProductBrand::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);
  }

  @CacheEvict(value = ProductBrand.CACHE_NAME, key = "#key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
