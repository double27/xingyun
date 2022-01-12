package com.lframework.xingyun.api.bo.basedata.customer;

import com.lframework.starter.web.bo.BaseBo;
import com.lframework.xingyun.basedata.dto.customer.CustomerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerSelectorBo extends BaseBo<CustomerDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 状态
     */
    private Boolean available;

    public CustomerSelectorBo() {

    }

    public CustomerSelectorBo(CustomerDto dto) {

        super(dto);
    }
}
