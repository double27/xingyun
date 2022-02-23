package com.lframework.xingyun.sc.vo.stock.take.pre;

import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.components.validation.TypeMismatch;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.starter.web.vo.BaseVo;
import com.lframework.xingyun.sc.enums.PreTakeStockSheetStatus;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class CreatePreTakeStockSheetVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @NotBlank(message = "请选择仓库！")
    private String scId;

    /**
     * 预先盘点状态
     */
    @NotNull(message = "请选择预先盘点状态！")
    @IsEnum(message = "预先盘点状态格式错误！", enumClass = PreTakeStockSheetStatus.class)
    @TypeMismatch(message = "预先盘点状态格式错误！")
    private Integer takeStatus;

    /**
     * 备注
     */
    private String description;

    /**
     * 商品
     */
    @Valid
    @NotEmpty(message = "请录入商品！")
    private List<PreTakeStockProductVo> products;

    @Override
    public void validate() {

        Set<String> checkSet = new HashSet<>();
        PreTakeStockSheetStatus takeStatus = EnumUtil.getByCode(PreTakeStockSheetStatus.class, this.getTakeStatus());
        for (int i = 0; i < this.getProducts().size(); i++) {
            PreTakeStockProductVo product = this.getProducts().get(i);
            if (checkSet.contains(product.getProductId())) {
                throw new InputErrorException("第" + (i + 1) + "行商品已存在列表中，请勿重复添加！");
            }
            
            checkSet.add(product.getProductId());

            if (takeStatus == PreTakeStockSheetStatus.FIRST_TAKE) {
                if (product.getFirstNum() == null) {
                    throw new InputErrorException("第" + (i + 1) + "行商品的初盘数量不允许为空！");
                }
            } else if (takeStatus == PreTakeStockSheetStatus.SECOND_TAKE) {
                if (product.getSecondNum() == null) {
                    throw new InputErrorException("第" + (i + 1) + "行商品的复盘数量不允许为空！");
                }
            } else if (takeStatus == PreTakeStockSheetStatus.RAND_TAKE) {
                if (product.getRandNum() == null) {
                    throw new InputErrorException("第" + (i + 1) + "行商品的抽盘数量不允许为空！");
                }
            } else {
                throw new InputErrorException("预先盘点状态格式错误！");
            }
        }
    }
}
