package com.lframework.xingyun.basedata.service.product;

import com.lframework.starter.web.service.BaseService;
import com.lframework.xingyun.basedata.dto.product.sale.ProductSaleDto;
import com.lframework.xingyun.basedata.vo.product.sale.CreateProductSaleVo;
import com.lframework.xingyun.basedata.vo.product.sale.UpdateProductSaleVo;

public interface IProductSaleService extends BaseService {

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    ProductSaleDto getById(String id);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateProductSaleVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateProductSaleVo vo);
}
