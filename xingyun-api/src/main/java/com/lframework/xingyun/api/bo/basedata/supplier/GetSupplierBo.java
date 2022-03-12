package com.lframework.xingyun.api.bo.basedata.supplier;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.xingyun.basedata.dto.supplier.SupplierDto;
import com.lframework.xingyun.core.dto.dic.city.DicCityDto;
import com.lframework.xingyun.core.service.IDicCityService;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSupplierBo extends BaseBo<SupplierDto> {

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
     * 助记码
     */
    private String mnemonicCode;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 邮编
     */
    private String zipCode;

    /**
     * 传真
     */
    private String fax;

    /**
     * 地区ID
     */
    private List<String> city;

    /**
     * 地区名称
     */
    private String cityName;

    /**
     * 地址
     */
    private String address;

    /**
     * 发货地址
     */
    private String deliveryAddress;

    /**
     * 送货周期（天）
     */
    private Integer deliveryCycle;

    /**
     * 经营方式
     */
    private Integer manageType;

    /**
     * 结账方式
     */
    private Integer settleType;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 纳税人识别号
     */
    private String taxIdentifyNo;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 户名
     */
    private String accountName;

    /**
     * 银行账号
     */
    private String accountNo;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetSupplierBo() {

    }

    public GetSupplierBo(SupplierDto dto) {

        super(dto);
    }

    @Override
    public BaseBo<SupplierDto> convert(SupplierDto dto) {

        return super.convert(dto, GetSupplierBo::getSettleType, GetSupplierBo::getManageType);
    }

    @Override
    protected void afterInit(SupplierDto dto) {

        if (dto.getManageType() != null) {
            this.manageType = dto.getManageType().getCode();
        }

        if (dto.getSettleType() != null) {
            this.settleType = dto.getSettleType().getCode();
        }

        if (!StringUtil.isBlank(dto.getCityId())) {
            IDicCityService dicCityService = ApplicationUtil.getBean(IDicCityService.class);
            List<DicCityDto> cityList = dicCityService.getChainById(dto.getCityId());
            this.city = cityList.stream().map(DicCityDto::getId).collect(Collectors.toList());

            this.cityName = cityList.stream().map(DicCityDto::getName).collect(Collectors.joining(StringPool.CITY_SPLIT));
        }
    }
}
