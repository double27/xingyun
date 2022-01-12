package com.lframework.xingyun.settle.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.*;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.xingyun.settle.dto.item.out.SettleOutItemDto;
import com.lframework.xingyun.settle.entity.SettleOutItem;
import com.lframework.xingyun.settle.mappers.SettleOutItemMapper;
import com.lframework.xingyun.settle.service.ISettleOutItemService;
import com.lframework.xingyun.settle.vo.item.out.CreateSettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.QuerySettleOutItemVo;
import com.lframework.xingyun.settle.vo.item.out.SettleOutItemSelectorVo;
import com.lframework.xingyun.settle.vo.item.out.UpdateSettleOutItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class SettleOutItemServiceImpl implements ISettleOutItemService {

    @Autowired
    private SettleOutItemMapper settleInItemMapper;

    @Override
    public PageResult<SettleOutItemDto> query(Integer pageIndex, Integer pageSize, QuerySettleOutItemVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleOutItemDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<SettleOutItemDto> query(QuerySettleOutItemVo vo) {

        return settleInItemMapper.query(vo);
    }

    @Override
    public PageResult<SettleOutItemDto> selector(Integer pageIndex, Integer pageSize, SettleOutItemSelectorVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<SettleOutItemDto> datas = settleInItemMapper.selector(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Cacheable(value = SettleOutItemDto.CACHE_NAME, key = "#id", unless = "#result == null")
    @Override
    public SettleOutItemDto getById(String id) {

        return settleInItemMapper.getById(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "停用支出项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchUnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getAvailable, Boolean.FALSE).in(SettleOutItem::getId, ids);
        settleInItemMapper.update(updateWrapper);

        ISettleOutItemService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "启用支出项目，ID：{}", params = "#ids", loopFormat = true)
    @Transactional
    @Override
    public void batchEnable(Collection<String> ids) {

        if (CollectionUtil.isEmpty(ids)) {
            return;
        }

        Wrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getAvailable, Boolean.TRUE).in(SettleOutItem::getId, ids);
        settleInItemMapper.update(updateWrapper);

        ISettleOutItemService thisService = getThis(this.getClass());
        for (String id : ids) {
            thisService.cleanCacheByKey(id);
        }
    }

    @OpLog(type = OpLogType.OTHER, name = "新增支出项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public String create(CreateSettleOutItemVo vo) {

        Wrapper<SettleOutItem> checkWrapper = Wrappers.lambdaQuery(SettleOutItem.class)
                .eq(SettleOutItem::getCode, vo.getCode());
        if (settleInItemMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        SettleOutItem data = new SettleOutItem();
        data.setId(IdUtil.getId());
        data.setCode(vo.getCode());
        data.setName(vo.getName());
        data.setAvailable(Boolean.TRUE);
        data.setDescription(StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

        settleInItemMapper.insert(data);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "修改支出项目，ID：{}, 编号：{}", params = {"#id", "#code"})
    @Transactional
    @Override
    public void update(UpdateSettleOutItemVo vo) {

        SettleOutItem data = settleInItemMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("支出项目不存在！");
        }

        Wrapper<SettleOutItem> checkWrapper = Wrappers.lambdaQuery(SettleOutItem.class)
                .eq(SettleOutItem::getCode, vo.getCode()).ne(SettleOutItem::getId, vo.getId());
        if (settleInItemMapper.selectCount(checkWrapper) > 0) {
            throw new DefaultClientException("编号重复，请重新输入！");
        }

        LambdaUpdateWrapper<SettleOutItem> updateWrapper = Wrappers.lambdaUpdate(SettleOutItem.class)
                .set(SettleOutItem::getCode, vo.getCode()).set(SettleOutItem::getName, vo.getName())
                .set(SettleOutItem::getAvailable, vo.getAvailable()).set(SettleOutItem::getDescription,
                        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
                .eq(SettleOutItem::getId, vo.getId());

        settleInItemMapper.update(updateWrapper);

        OpLogUtil.setVariable("id", data.getId());
        OpLogUtil.setVariable("code", vo.getCode());
        OpLogUtil.setExtra(vo);

        ISettleOutItemService thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(data.getId());
    }

    @CacheEvict(value = SettleOutItemDto.CACHE_NAME, key = "#key")
    @Override
    public void cleanCacheByKey(String key) {

    }
}
