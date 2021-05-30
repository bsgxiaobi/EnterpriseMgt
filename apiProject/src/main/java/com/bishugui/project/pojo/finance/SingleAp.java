package com.bishugui.project.pojo.finance;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bishugui.project.pojo.customer.CustomerInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SingleAp implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private CustomerInfo customerInfo;
    /**
     * 应付单单号
     */
    @TableId
    private String sapId;

    /**
     * 客户id
     */
    private Integer cid;

    /**
     * 应付金额
     */
    private Double cope;

    /**
     * 已付金额
     */
    private Double paid;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 经手人
     */
    private Integer agent;

    /**
     * 制单时间
     */
    private LocalDateTime createDate;

    private Integer createId;

    private String agentName;
    private String createName;
}
