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
public class RewardsPunishRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Employees employees;
    /**
     * 奖罚id-自增
     */
    @TableId(value = "rwr_id", type = IdType.AUTO)
    private Integer rwrId;

    /**
     * 被执行员工id-引用employee表
     */
    private Integer employeeId;

    /**
     * 奖罚类型
     */
    private String type;

    /**
     * 处理类别
     */
    private String category;

    /**
     * 奖罚金额
     */
    private Integer amount;

    /**
     * 奖罚方式
     */
    private String way;

    /**
     * 奖罚原因
     */
    private String cause;

    /**
     * 奖罚备注
     */
    private String note;

    /**
     * 奖罚日期
     */
    private LocalDateTime applyDate;

    /**
     * 发布者id
     */
    private Integer applyId;

    private String applyState;

    private Integer updateId;
    private LocalDateTime updateDate;


}
