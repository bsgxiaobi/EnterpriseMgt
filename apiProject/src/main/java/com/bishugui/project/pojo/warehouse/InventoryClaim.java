package com.bishugui.project.pojo.warehouse;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bishugui.project.pojo.Employees;
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
public class InventoryClaim implements Serializable {

    @TableField(exist = false)
    private List<ClaimItem> claimItemList;
    /**
     * 支领发放单主键
     */
    @TableId
    private String claimId;

    /**
     * 申请人id
     */
    private Integer applyId;

    /**
     * 经手人id
     */
    private Integer agentId;

    /**
     * 申请时间
     */
    private LocalDateTime applyDate;

    /**
     * 经受日期
     */
    private LocalDateTime agentDate;

    /**
     * 支领类型：发放、申请
     */
    private String claimType;

    /**
     * 原因
     */
    private String cause;

    /**
     * 申请状态：待批准、待领取、完成
     */
    private String applyState;

    /**
     * 发放总价
     */
    private Double claimTotal;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * 领取日期
     */
    private LocalDateTime receiveDate;
}
