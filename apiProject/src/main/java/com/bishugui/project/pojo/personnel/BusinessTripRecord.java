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
public class BusinessTripRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String startDateTemp;

    @TableField(exist = false)
    private String endDateTemp;

    @TableField(exist = false)
    private Employees employees;
    /**
     * 出差记录id-自增
     */
    @TableId(value = "bt_id", type = IdType.AUTO)
    private Integer btId;

    /**
     * 员工id-引用employee表
     */
    private Integer employeeId;

    /**
     * 出差起始时间
     */
    private LocalDateTime startDate;

    /**
     * 出差结束时间
     */
    private LocalDateTime endDate;

    /**
     * 出差地点
     */
    private String site;

    /**
     * 出差原因
     */
    private String cause;

    /**
     * 出差申请时间
     */
    private LocalDateTime applyDate;

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
     * 交通工具
     */
    private String vehicle;
    /**
     * 预支金额
     */
    private Integer advance;

    /**
     * 报销金额
     */
    private Integer usedAmount;

    /**
     * 退补金额
     */
    private Integer refund;
}
