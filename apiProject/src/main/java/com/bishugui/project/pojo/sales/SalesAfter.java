package com.bishugui.project.pojo.sales;

import java.time.LocalDateTime;
import java.io.Serializable;

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
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SalesAfter implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 销售售后单
     */
    @TableId
    private String saId;

    /**
     * 关联销售单
     */
    private String sqId;

    /**
     * 售后原因
     */
    private String cause;

    /**
     * 经办人
     */
    private Integer agent;

    /**
     * 经办时间
     */
    private LocalDateTime agentDate;

    /**
     * 处理结果
     */
    private String results;

    /**
     * 优先级，高、中、低
     */
    private String priority;

    /**
     * 状态：待处理、处理中、处理完成
     */
    private String state;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 责任部门
     */
    private String belongDepartment;

    /**
     * 申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 解决方案
     */
    private String solution;

    /**
     * 反馈意见
     */
    private String feedback;

    /**
     * 满意度
     */
    private String satisfaction;

    /**
     * 是否回访,0、1
     */
    private Boolean isVisit;

    /**
     * 改进意见
     */
    private String improvement;

    /**
     * 客户
     */
    private String customer;

}
