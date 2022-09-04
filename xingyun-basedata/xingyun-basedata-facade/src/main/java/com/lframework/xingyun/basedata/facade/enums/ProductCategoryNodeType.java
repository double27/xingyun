package com.lframework.xingyun.basedata.facade.enums;

import com.lframework.starter.mybatis.enums.system.NodeType;
import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public final class ProductCategoryNodeType implements NodeType, Serializable {

  private static final long serialVersionUID = 1L;

  @Override
  public Integer getCode() {

    return 2;
  }

  @Override
  public String getDesc() {

    return "商品类目";
  }
}
