package com.bishugui.project.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Position> positionList;
    /**
     * 职位id-自增
     */
    @TableId(value = "position_id", type = IdType.AUTO)
    private Integer positionId;

    /**
     * 部门id-引用department表
     */
    private Integer departmentId;

    /**
     * 职位名称
     */
    private String jobTitle;

    /**
     * 岗位职责
     */
    private String duty;

    /**
     * 更新日期
     */
    private LocalDateTime updateDate;

    /**
     * 经手人
     */
    private Integer updateId;

    /**
     * 父职位
     */
    private Integer parentId;


}
