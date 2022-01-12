package com.lframework.xingyun.basedata.vo.supplier;

import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.xingyun.basedata.enums.ManageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySupplierSelectorVo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 经营方式
     */
    @IsEnum(message = "经营方式格式不正确！", enumClass = ManageType.class)
    private Integer manageType;

    /**
     * 状态
     */
    private Boolean available;
}
