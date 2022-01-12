package com.lframework.xingyun.sc.vo.sale.returned;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.sc.dto.sale.returned.SaleReturnDto;
import com.lframework.xingyun.sc.service.sale.ISaleReturnService;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateSaleReturnVo extends CreateSaleReturnVo {

    private static final long serialVersionUID = 1L;

    /**
     * 退单ID
     */
    @NotBlank(message = "退单ID不能为空！")
    private String id;

    @Override
    public void validate() {

        ISaleReturnService saleReturnService = ApplicationUtil.getBean(ISaleReturnService.class);
        SaleReturnDto saleReturn = saleReturnService.getById(this.getId());

        this.validate(!StringUtil.isBlank(saleReturn.getOutSheetId()));
    }
}
