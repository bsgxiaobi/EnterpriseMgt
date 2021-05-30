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
public class ApplyLeaveRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String startDateTemp;

    @TableField(exist = false)
    private String endDateTemp;

    @TableField(exist = false)
    private Employees employees;
    /**
     * 请假申请记录id-自增
     */
    @TableId(value = "alr_id", type = IdType.AUTO)
    private Integer alrId;

    /**
     * 员工id-employee表
     */
    private Integer employeeId;

    /**
     * 请假申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 请假开始日期
     */
    private LocalDateTime startDate;

    /**
     * 请假结束日期
     */
    private LocalDateTime endDate;

    /**
     * 请假原因
     */
    private String cause;

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
    private String note;

    /**
     * 请假类型
     */
    private String type;


}
