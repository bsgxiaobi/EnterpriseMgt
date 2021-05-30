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
public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Warehouse warehouse;

    @TableField(exist = false)
    WarehouseShelves shelves;
    /**
     * 物品自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer iiId;

    /**
     * 物品编码
     */
    private String productCode;

    /**
     * 所属仓库id
     */
    private Integer warehouseId;

    /**
     * 所属货架id
     */
    private Integer shelvesId;

    /**
     * 物品名称
     */
    private String title;

    /**
     * 物品描述
     */
    private String abstracts;

    /**
     * 物品规格类型
     */
    private String model;

    /**
     * 单位
     */
    private String unit;

    /**
     * 库存总数
     */
    private Integer sum;

    /**
     * 单价
     */
    private Double price;

    /**
     * 总价值
     */
    private Double total;
}
