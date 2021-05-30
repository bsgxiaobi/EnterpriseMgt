package com.bishugui.project.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PurchaseApply implements Serializable {

    private static final long serialVersionUID = 1L;

    //采购申请表需要关联一个员工信息
    @TableField(exist = false)
    private Employees employees;

    //采购申请表带有一个采购清单
    @TableField(exist = false)
    private List<PurchaseItem> itemList;


    /**
     * 采购申请单编号
     */
    @TableId()
    private String poId;

    /**
     * 申请员工id-employees表
     */
    private Integer employeeId;

    /**
     * 审核状态，0为待审核，1为批准，-1为审核未通过
     */
    private String state;

    /**
     * 采购申请原因
     */
    private String cause;

    /**
     * 采购申请日期
     */
    private LocalDateTime applyDate;

    /**
     * 批准人
     */
    private Integer approver;

    /**
     * 批准日期
     */
    private LocalDateTime approvalDate;

}
