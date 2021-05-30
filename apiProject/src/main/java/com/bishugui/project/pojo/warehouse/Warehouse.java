package com.bishugui.project.pojo.warehouse;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库id
     */
    @TableId(value = "warehouse_id", type = IdType.AUTO)
    private Integer warehouseId;

    /**
     * 仓库名称
     */
    private String warehouseName;

    /**
     * 更新日期
     */
    private LocalDateTime updateDate;

    /**
     * 经手人
     */
    private Integer agentId;

    /**
     * 介绍
     */
    private String introduce;

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
