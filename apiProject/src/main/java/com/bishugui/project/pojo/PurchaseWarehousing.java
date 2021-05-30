package com.bishugui.project.pojo;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

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
public class PurchaseWarehousing implements Serializable {

    private static final long serialVersionUID = 1L;

    //包含采购询价单
    @TableField(exist = false)
    PurchaseInquiry purchaseInquiry;

    //包含清单详情
    @TableField(exist = false)
    List<PurchaseItem> purchaseItemList;

    /**
     * 采购入库单编号
     */
    @TableId
    private String pwId;

    /**
     * 采购申请单编号
     */
    private String poId;

    /**
     * 采购询价单编号
     */
    private String piId;

    /**
     * 采购入库单经办人
     */
    private Integer agentId;

    /**
     * 采购入库单经办日期
     */
    private LocalDateTime agentDate;

    /**
     * 采购入库单备注
     */
    private String note;

    /**
     * 采购入库仓库
     */
    private String warehouse;

    /**
     * 入库金额
     */
    private Double warehousingTotal;

    /**
     * 入库状态
     */
    private String warehousingState;

    public PurchaseWarehousing(PurchaseInquiry purchaseInquiry) {
        this.purchaseInquiry = purchaseInquiry;
    }
}
