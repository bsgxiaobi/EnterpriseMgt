package com.bishugui.project.pojo.sales;

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
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class SalesQuotation implements Serializable {

    private static final long serialVersionUID = 1L;

    //包含销售清单
    @TableField(exist = false)
    private List<SalesItem> salesItemList;

    //包含员工信息
    @TableField(exist = false)
    private Employees employees;

    /**
     * 销售报价单单号
     */
    @TableId
    private String sqId;

    /**
     * 客户
     */
    private String customer;
    private Integer cid;

    /**
     * 销售类型
     */
    private String saleType;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 结算方式
     */
    private String settlementWay;

    /**
     * 销售报价单经手人
     */
    private Integer agent;

    /**
     * 销售报价单经手日期
     */
    private LocalDateTime agentDate;

    private String note;

    /**
     * 出库经手人
     */
    private Integer deliveryAgent;

    /**
     * 出库经手时间
     */
    private LocalDateTime deliveryAgentDate;

    /**
     * 出库备注
     */
    private String deliveryNote;

    /**
     * 出库总价
     */
    private Double deliveryTotal;

    /**
     * 发货仓库
     */
    private String warehouse;

    /**
     * 销售询价单总价
     */
    private Double total;

    /**
     * 状态
     */
    private String state;

    /**
     * 到货方式
     */
    private String arrivalWay;

}
