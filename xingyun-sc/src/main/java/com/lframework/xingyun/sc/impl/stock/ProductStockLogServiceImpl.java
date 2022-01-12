package com.lframework.xingyun.sc.impl.stock;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.xingyun.sc.dto.stock.ProductStockLogDto;
import com.lframework.xingyun.sc.entity.ProductStockLog;
import com.lframework.xingyun.sc.enums.ProductStockBizType;
import com.lframework.xingyun.sc.mappers.ProductStockLogMapper;
import com.lframework.xingyun.sc.service.stock.IProductStockLogService;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithAddStockVo;
import com.lframework.xingyun.sc.vo.stock.log.AddLogWithSubStockVo;
import com.lframework.xingyun.sc.vo.stock.log.QueryProductStockLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductStockLogServiceImpl implements IProductStockLogService {

    @Autowired
    private ProductStockLogMapper productStockLogMapper;

    @Override
    public PageResult<ProductStockLogDto> query(Integer pageIndex, Integer pageSize, QueryProductStockLogVo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List<ProductStockLogDto> datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }

    @Override
    public List<ProductStockLogDto> query(QueryProductStockLogVo vo) {

        return productStockLogMapper.query(vo);
    }

    @Transactional
    @Override
    public void addLogWithAddStock(AddLogWithAddStockVo vo) {

        ProductStockLog record = new ProductStockLog();
        record.setId(IdUtil.getId());
        record.setScId(vo.getScId());
        record.setProductId(vo.getProductId());
        record.setLotId(vo.getLotId());
        record.setOriStockNum(vo.getOriStockNum());
        record.setCurStockNum(vo.getCurStockNum());
        record.setOriTaxPrice(vo.getOriTaxPrice());
        record.setCurTaxPrice(vo.getCurTaxPrice());
        record.setOriUnTaxPrice(vo.getOriUnTaxPrice());
        record.setCurUnTaxPrice(vo.getCurUnTaxPrice());
        record.setStockNum(vo.getStockNum());
        record.setTaxAmount(vo.getTaxAmount());
        record.setUnTaxAmount(vo.getUnTaxAmount());
        if (!StringUtil.isBlank(vo.getCreateBy())) {
            record.setCreateBy(vo.getCreateBy());
        }
        record.setCreateTime(vo.getCreateTime());
        if (!StringUtil.isBlank(vo.getBizId())) {
            record.setBizId(vo.getBizId());
        }
        if (!StringUtil.isBlank(vo.getBizDetailId())) {
            record.setBizDetailId(vo.getBizDetailId());
        }
        if (!StringUtil.isBlank(vo.getBizCode())) {
            record.setBizCode(vo.getBizCode());
        }
        record.setBizType(EnumUtil.getByCode(ProductStockBizType.class, vo.getBizType()));

        productStockLogMapper.insert(record);
    }

    @Transactional
    @Override
    public void addLogWithSubStock(AddLogWithSubStockVo vo) {

        ProductStockLog record = new ProductStockLog();
        record.setId(IdUtil.getId());
        record.setScId(vo.getScId());
        record.setProductId(vo.getProductId());
        record.setLotId(vo.getLotId());
        record.setOriStockNum(vo.getOriStockNum());
        record.setCurStockNum(vo.getCurStockNum());
        record.setOriTaxPrice(vo.getOriTaxPrice());
        record.setCurTaxPrice(vo.getCurTaxPrice());
        record.setOriUnTaxPrice(vo.getOriUnTaxPrice());
        record.setCurUnTaxPrice(vo.getCurUnTaxPrice());
        record.setStockNum(-Math.abs(vo.getStockNum()));
        record.setTaxAmount(vo.getTaxAmount().abs().negate());
        record.setUnTaxAmount(vo.getUnTaxAmount().abs().negate());
        if (!StringUtil.isBlank(vo.getCreateBy())) {
            record.setCreateBy(vo.getCreateBy());
        }
        record.setCreateTime(vo.getCreateTime());
        if (!StringUtil.isBlank(vo.getBizId())) {
            record.setBizId(vo.getBizId());
        }
        if (!StringUtil.isBlank(vo.getBizDetailId())) {
            record.setBizDetailId(vo.getBizDetailId());
        }
        if (!StringUtil.isBlank(vo.getBizCode())) {
            record.setBizCode(vo.getBizCode());
        }
        record.setBizType(EnumUtil.getByCode(ProductStockBizType.class, vo.getBizType()));

        productStockLogMapper.insert(record);
    }
}
