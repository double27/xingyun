package com.lframework.xingyun.settle.vo.sheet;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ApprovePassSettleSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空！")
    private String id;

    /**
     * 备注
     */
    private String description;
}
