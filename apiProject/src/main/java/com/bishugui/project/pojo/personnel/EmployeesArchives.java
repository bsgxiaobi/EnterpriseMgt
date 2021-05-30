package com.bishugui.project.pojo.personnel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bishugui.project.pojo.Employees;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class EmployeesArchives implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private String contractStartTemp;


    @TableField(exist = false)
    private Employees employees;
    /**
     * 职员id-引用employees表
     */
    @TableId
    private Integer employeeId;

    /**
     * 入离职，入职、离职
     */
    private String joinLeave;

    /**
     * 试用期:一个月、两个月、三个月、不定
     */
    private String trialPeriod;

    /**
     * 合同开始日期
     */
    private LocalDateTime contractStart;

    /**
     * 合同期限
     */
    private String term;

    /**
     * 聘用渠道
     */
    private String channel;

    /**
     * 试用期工资，为正式合同工资的80%
     */
    private BigDecimal trialSalary;

    /**
     * 转正工资
     */
    private BigDecimal formalSalary;

    /**
     * 社会保险号
     */
    private String socialId;

    /**
     * 住房公积金号
     */
    private String housing;

    /**
     * 就业登记证号
     */
    private String employmentId;

    /**
     * 离职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime leaveDate;

    /**
     * 离职申请日期
     */
    private LocalDateTime leaveApplyDate;

    /**
     * 离职原因
     */
    private String leaveCause;

    /**
     * 入职经办人
     */
    private String joinAgent;

    /**
     * 入职经办日期
     */
    private LocalDateTime joinHandlingDate;

    /**
     * 离职经办人
     */
    private String leaveAgent;

    /**
     * 离职经办日期
     */
    private LocalDateTime leaveHandlingDate;

    /**
     * 最高学历
     */
    private String academic;

    /**
     * 毕业学校
     */
    private String graduatedSchool;

    /**
     * 专业
     */
    private String speciality;

    /**
     * 简历
     */
    private String resume;


    private LocalDateTime updateDate;

    private Integer updateId;


}
