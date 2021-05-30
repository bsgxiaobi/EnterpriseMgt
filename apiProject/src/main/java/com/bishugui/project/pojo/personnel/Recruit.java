package com.bishugui.project.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.bishugui.project.pojo.Employees;
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
public class Recruit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Employees employees;

    @TableField(exist = false)
    private Department department;
    /**
     * 招聘id-自增
     */
    @TableId(value = "recruit_id", type = IdType.AUTO)
    private Integer recruitId;

    /**
     * 申请人id
     */
    private Integer applyId;

    private Integer departmentId;

    private LocalDateTime applyDate;

    /**
     * 职位
     */
    private String post;

    /**
     * 需求人数
     */
    private Integer num;

    /**
     * 所需专业
     */
    private String speciality;

    /**
     * 岗位职责
     */
    private String duty;

    /**
     * 具体要求
     */
    private String requirement;

    /**
     * 薪资范围
     */
    private String salary;

    private String applyState;

    private LocalDateTime updateDate;

    private Integer updateId;


}
