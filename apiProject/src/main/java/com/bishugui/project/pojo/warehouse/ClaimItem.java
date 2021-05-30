package com.bishugui.project.pojo.warehouse;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClaimItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "item_id", type = IdType.AUTO)
    private Integer itemId;

    private String claimId;

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
     * 仓库单价
     */
    private Double price;

    /**
     * 数量
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


}
