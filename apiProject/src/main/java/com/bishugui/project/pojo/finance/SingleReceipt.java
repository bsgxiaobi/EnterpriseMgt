package com.bishugui.project.pojo.finance;

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
 * @since 2021-05-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SingleReceipt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 收款单单号
     */
    @TableId
    private String srId;

    /**
     * 应收单单号
     */
    private String sarId;

    /**
     * 收款类型
     */
    private String type;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 账户名称
     */
    private String accountTitle;

    /**
     * 金额
     */
    private Double total;

    /**
     * 经办人
     */
    private String agent;

    /**
     * 制单时间
     */
    private LocalDateTime createDate;

    private Integer createId;

    private String agentName;
    private String createName;
}
