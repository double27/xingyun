package com.lframework.xingyun.basedata.impl.product;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.xingyun.basedata.dto.product.category.property.ProductCategoryPropertyDto;
import com.lframework.xingyun.basedata.dto.product.poly.ProductPolyPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.ProductPropertyDto;
import com.lframework.xingyun.basedata.dto.product.property.item.ProductPropertyItemDto;
import com.lframework.xingyun.basedata.entity.ProductPolyProperty;
import com.lframework.xingyun.basedata.enums.ColumnType;
import com.lframework.xingyun.basedata.mappers.ProductPolyPropertyMapper;
import com.lframework.xingyun.basedata.service.product.*;
import com.lframework.xingyun.basedata.vo.product.poly.property.CreateProductPolyPropertyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductPolyPropertyServiceImpl implements IProductPolyPropertyService {

    @Autowired
    private ProductPolyPropertyMapper productPolyPropertyMapper;

    @Autowired
    private IProductPropertyService productPropertyService;

    @Autowired
    private IProductPropertyItemService productPropertyItemService;

    @Autowired
    private IProductPolyService productPolyService;

    @Autowired
    private IProductCategoryPropertyService productCategoryPropertyService;

    @Override
    public List<ProductPolyPropertyDto> getByPolyId(String polyId) {

        return productPolyPropertyMapper.getByPolyId(polyId);
    }

    @Transactional
    @Override
    public void setMultipleToSimple(String propertyId) {

        List<ProductPolyPropertyDto> datas = productPolyPropertyMapper.getByPropertyId(propertyId);
        if (!CollectionUtil.isEmpty(datas)) {

            Set<ProductPolyPropertyDto> checkSet = new HashSet<>();
            for (ProductPolyPropertyDto data : datas) {
                if (checkSet.stream().anyMatch(t -> t.getPolyId().equals(data.getPolyId()) && t.getPropertyId()
                        .equals(data.getPropertyId()))) {
                    productPolyPropertyMapper.deleteById(data.getId());
                } else {
                    checkSet.add(data);
                }
            }
        }
    }

    @Transactional
    @Override
    public void setCommonToAppoint(String propertyId) {

        ProductPropertyDto productProperty = productPropertyService.getById(propertyId);
        List<ProductCategoryPropertyDto> categoryList = productCategoryPropertyService.getByPropertyId(propertyId);
        for (ProductCategoryPropertyDto productCategoryPropertyDto : categoryList) {
            productPolyPropertyMapper.setCommonToAppoint(propertyId, productCategoryPropertyDto.getCategoryId());
        }
    }

    @Transactional
    @Override
    public void setAppointToCommon(String propertyId) {

        List<ProductPropertyItemDto> propertyItems = productPropertyItemService.getByPropertyId(propertyId);
        if (!CollectionUtil.isEmpty(propertyItems)) {
            ProductPropertyItemDto propertyItem = propertyItems.get(0);

            List<String> polyIds = productPolyService.getIdNotInPolyProperty(propertyId);
            if (!CollectionUtil.isEmpty(polyIds)) {
                for (String polyId : polyIds) {
                    ProductPolyProperty data = new ProductPolyProperty();
                    data.setId(IdUtil.getId());
                    data.setPolyId(polyId);
                    data.setPropertyId(propertyItem.getPropertyId());
                    data.setPropertyItemId(propertyItem.getId());

                    productPolyPropertyMapper.insert(data);
                }
            }

        }
    }

    @Transactional
    @Override
    public void updateAppointCategoryId(String propertyId) {

        List<ProductPropertyItemDto> propertyItems = productPropertyItemService.getByPropertyId(propertyId);
        if (!CollectionUtil.isEmpty(propertyItems)) {
            ProductPropertyItemDto propertyItem = propertyItems.get(0);

            Wrapper<ProductPolyProperty> deleteWrapper = Wrappers.lambdaQuery(ProductPolyProperty.class)
                    .eq(ProductPolyProperty::getPropertyId, propertyId);
            productPolyPropertyMapper.delete(deleteWrapper);

            ProductPropertyDto productProperty = productPropertyService.getById(propertyId);

            List<ProductCategoryPropertyDto> categoryList = productCategoryPropertyService.getByPropertyId(propertyId);
            for (ProductCategoryPropertyDto productCategoryPropertyDto : categoryList) {
                List<String> polyIds = productPolyService.getIdByCategoryId(productCategoryPropertyDto.getCategoryId());
                if (!CollectionUtil.isEmpty(polyIds)) {
                    for (String polyId : polyIds) {
                        ProductPolyProperty data = new ProductPolyProperty();
                        data.setId(IdUtil.getId());
                        data.setPolyId(polyId);
                        data.setPropertyId(propertyItem.getPropertyId());
                        data.setPropertyItemId(propertyItem.getId());

                        productPolyPropertyMapper.insert(data);
                    }
                }
            }
        }
    }

    @Transactional
    @Override
    public String create(CreateProductPolyPropertyVo vo) {

        ProductPropertyDto productProperty = productPropertyService.getById(vo.getPropertyId());
        if (productProperty == null) {
            throw new DefaultClientException("商品属性不存在！");
        }

        ProductPolyProperty data = new ProductPolyProperty();
        data.setId(IdUtil.getId());
        data.setPolyId(vo.getPolyId());
        data.setPropertyId(productProperty.getId());

        if (productProperty.getColumnType() != ColumnType.CUSTOM) {
            ProductPropertyItemDto propertyItem = productPropertyItemService.getById(vo.getPropertyItemId());

            if (propertyItem == null) {
                throw new DefaultClientException("属性值不存在！");
            }
            Assert.isTrue(propertyItem.getPropertyId().equals(productProperty.getId()));

            data.setPropertyItemId(propertyItem.getId());

        } else {
            if (StringUtil.isBlank(vo.getPropertyText())) {
                throw new DefaultClientException("属性值文本不存在！");
            }

            data.setPropertyText(vo.getPropertyText());
        }

        productPolyPropertyMapper.insert(data);

        return data.getId();
    }
}
