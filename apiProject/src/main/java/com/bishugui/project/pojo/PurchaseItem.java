package com.bishugui.project.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 采购申请物品清单列表id-自增
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    /**
     * 采购申请单id
     */
    private String poId;

    /**
     * 物品id
     */
    private Integer pId;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 规格型号
     */
    private String model;

    /**
     * 单位
     */
    private String pic;

    /**
     * 预算单价
     */
    private Double budgetPrice;

    /**
     * 申请数量
     */
    private Integer budgetNum;

    /**
     * 预算总价
     */
    private Double budgetTotal;

    /**
     * 剩余数量
     */
    private Integer surplusNum;

    /**
     * 核准数量
     */
    private Integer approvalNum;

    /**
     * 购买单价
     */
    private Double purchasePrice;

    /**
     * 采购总价
     */
    private Double purchaseTotal;

    /**
     * 入库数量
     */
    private Integer warehousingNum;

    /**
     * 入库金额
     */
    private Double warehousingTotal;

}
