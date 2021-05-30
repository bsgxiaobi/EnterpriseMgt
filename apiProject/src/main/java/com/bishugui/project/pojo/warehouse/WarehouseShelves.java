package com.bishugui.project.pojo.warehouse;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class WarehouseShelves implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<WarehouseShelves> shelvesList;
    /**
     * 货架号
     */
    @TableId(type = IdType.AUTO)
    private Integer shelvesId;

    private String shelvesName;
    /**
     * 上级货架号
     */
    private Integer parentId;

    /**
     * 说明
     */
    private String note;

    /**
     * 所属仓库
     */
    private Integer warehouseId;

    /**
     * 更新日期
     */
    private LocalDateTime updateDate;

    /**
     * 经手人
     */
    private Integer agentId;


}
