package com.bishugui.project.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class PurchaseInquiry implements Serializable {

    private static final long serialVersionUID = 1L;

    //包含采购申请单信息
    @TableField(exist = false)
    private PurchaseApply purchaseApply;
    /**
     * 采购询价单编号
     */
    @TableId
    private String piId;

    /**
     * 采购申请单编号
     */
    private String poId;

    /**
     * 经办人
     */
    private Integer agentId;

    /**
     * 经办日期
     */
    private LocalDateTime agentDate;

    /**
     * 供应商id
     */
    private Integer supplierId;

    /**
     * 采购方式
     */
    private String puchaseWay;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 结算方式
     */
    private String settlementWay;

    /**
     * 到货方式
     */
    private String arrivalWay;

    /**
     * 采购询价单备注
     */
    private String note;

    /**
     * 询价状态：未询价、待审核、审核驳回
     */
    private String inquiryState;

    /**
     * 询价总金额
     */
    private Double inquiryTotal;

    /**
     * 审核人
     */
    private Integer approval;

    /**
     * 审核时间
     */
    private LocalDateTime approvalDate;

    public PurchaseInquiry(PurchaseApply purchaseApply) {
        this.purchaseApply = purchaseApply;
    }
}
