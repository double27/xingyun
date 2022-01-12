package com.lframework.xingyun.basedata.impl.product;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.xingyun.basedata.dto.product.retail.ProductRetailDto;
import com.lframework.xingyun.basedata.entity.ProductRetail;
import com.lframework.xingyun.basedata.mappers.ProductRetailMapper;
import com.lframework.xingyun.basedata.service.product.IProductRetailService;
import com.lframework.xingyun.basedata.vo.product.retail.CreateProductRetailVo;
import com.lframework.xingyun.basedata.vo.product.retail.UpdateProductRetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductRetailServiceImpl implements IProductRetailService {

    @Autowired
    private ProductRetailMapper productRetailMapper;

    @Override
    public ProductRetailDto getById(String id) {

        return productRetailMapper.getById(id);
    }

    @OpLog(type = OpLogType.OTHER, name = "设置商品零售价，ID：{}, 零售价：{}", params = {"#vo.id", "#vo.price"})
    @Transactional
    @Override
    public String create(CreateProductRetailVo vo) {

        ProductRetail data = new ProductRetail();
        data.setId(IdUtil.getId());
        if (!StringUtil.isBlank(vo.getId())) {
            data.setId(vo.getId());
        }

        data.setPrice(vo.getPrice());

        productRetailMapper.insert(data);

        return data.getId();
    }

    @OpLog(type = OpLogType.OTHER, name = "设置商品零售价，ID：{}, 零售价：{}", params = {"#vo.id", "#vo.price"})
    @Transactional
    @Override
    public void update(UpdateProductRetailVo vo) {

        if (vo.getPrice() == null) {
            throw new InputErrorException("零售价不能为空！");
        }

        if (vo.getPrice().doubleValue() < 0D) {
            throw new InputErrorException("零售价必须大于0！");
        }

        productRetailMapper.deleteById(vo.getId());

        CreateProductRetailVo createVo = new CreateProductRetailVo();
        createVo.setId(vo.getId());
        createVo.setPrice(vo.getPrice());

        this.create(createVo);
    }
}
