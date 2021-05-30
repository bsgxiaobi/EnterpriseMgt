package com.bishugui.project.pojo.sales;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SalesItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售物品清单列表id-自增
     */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    /**
     * 销售报价单id
     */
    private String sqId;

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
    private Double price;

    /**
     * 申请数量
     */
    private Integer num;

    /**
     * 总价
     */
    private Double total;

    /**
     * 剩余数量
     */
    private Integer surplusNum;

    /**
     * 销售出库数量
     */
    private Integer deliveryNum;


}
