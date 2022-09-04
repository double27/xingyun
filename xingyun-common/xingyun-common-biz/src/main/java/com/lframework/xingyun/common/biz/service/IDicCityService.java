package com.lframework.xingyun.common.biz.service;

import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.xingyun.common.facade.dto.dic.city.DicCityDto;
import com.lframework.xingyun.common.facade.entity.DicCity;
import java.util.List;

public interface IDicCityService extends BaseMpService<DicCity> {

  /**
   * 查询所有数据
   *
   * @return
   */
  List<DicCityDto> getAll();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DicCityDto findById(String id);

  /**
   * 根据ID查询链路数据
   *
   * @param id 末级ID
   * @return 返回值顺序：省、市、区
   */
  List<DicCityDto> getChainById(String id);
}
