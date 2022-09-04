package com.lframework.xingyun.basedata.biz.service.shop;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.basedata.facade.entity.Shop;
import com.lframework.xingyun.basedata.facade.vo.shop.CreateShopVo;
import com.lframework.xingyun.basedata.facade.vo.shop.QueryShopVo;
import com.lframework.xingyun.basedata.facade.vo.shop.UpdateShopVo;
import java.util.List;

/**
 * 门店 Service
 *
 * @author zmj
 */
public interface IShopService extends BaseMpService<Shop> {

  /**
   * 查询列表
   * @return
   */
  PageResult<Shop> query(Integer pageIndex, Integer pageSize, QueryShopVo vo);

  /**
   * 查询列表
   * @param vo
   * @return
   */
  List<Shop> query(QueryShopVo vo);

  /**
   * 根据ID查询
   * @param id
   * @return
   */
  Shop findById(String id);

  /**
   * 创建
   * @param vo
   * @return
   */
  String create(CreateShopVo vo);

  /**
   * 修改
   * @param vo
   */
  void update(UpdateShopVo vo);

}
