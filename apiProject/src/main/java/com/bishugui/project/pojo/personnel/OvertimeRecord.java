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
 * @since 2021-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OvertimeRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private String startDateTemp;

    @TableField(exist = false)
    private Employees employees;
    /**
     * 加班记录id-自增
     */
    @TableId(value = "or_id", type = IdType.AUTO)
    private Integer orId;

    /**
     * 员工id-employee表
     */
    private Integer employeeId;

    /**
     * 申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 加班日期
     */
    private LocalDateTime startDate;

    /**
     * 申请加班的小时数
     */
    private Integer applyHours;

    /**
     * 批准人
     */
    private String approver;

    /**
     * 批准日期
     */
    private LocalDateTime approvalDate;

    /**
     * 申请状态，已申请、未通过、通过
     */
    private String applyState;

    /**
     * 备注
     */
    private String cause;


}
