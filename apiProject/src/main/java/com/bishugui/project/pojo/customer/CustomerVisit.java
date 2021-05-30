package com.bishugui.project.pojo.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    CustomerContacts customerContacts;

    @TableField(exist = false)
    CustomerInfo customerInfo;

    /**
     * 回访记录id
     */
    @TableId(value = "cv_id", type = IdType.AUTO)
    private Integer cvId;

    /**
     * 回访客户
     */
    private Integer cid;

    /**
     * 回访联系人
     */
    private Integer ccId;

    /**
     * 回访类型
     */
    private String type;

    /**
     * 回访内容
     */
    private String content;

    /**
     * 回访总结
     */
    private String summary;

    /**
     * 回访日期
     */
    private LocalDateTime visitDate;

    /**
     * 登记人
     */
    private Integer agent;

    /**
     * 登记日期
     */
    private LocalDateTime agentDate;


}
