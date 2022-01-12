package com.lframework.xingyun.api.controller.purchase;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.xingyun.api.bo.purchase.config.GetPurchaseConfigBo;
import com.lframework.xingyun.sc.dto.purchase.config.PurchaseConfigDto;
import com.lframework.xingyun.sc.service.purchase.IPurchaseConfigService;
import com.lframework.xingyun.sc.vo.purchase.config.UpdatePurchaseConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 采购参数设置
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/purchase/config")
public class PurchaseConfigController extends DefaultBaseController {

    @Autowired
    private IPurchaseConfigService purchaseConfigService;

    @GetMapping
    public InvokeResult get() {

        PurchaseConfigDto config = purchaseConfigService.get();
        GetPurchaseConfigBo result = new GetPurchaseConfigBo(config);

        return InvokeResultBuilder.success(result);
    }

    @PutMapping
    public InvokeResult update(@Valid UpdatePurchaseConfigVo vo) {

        purchaseConfigService.update(vo);

        return InvokeResultBuilder.success();
    }
}
