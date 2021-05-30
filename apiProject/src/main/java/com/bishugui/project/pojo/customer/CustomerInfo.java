package com.bishugui.project.pojo.customer;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户单位id-自增
     */
    @TableId(type = IdType.AUTO)
    private Integer cid;

    /**
     * 客户信息创建人
     */
    private Integer createId;
    /**
     * 信息更新人
     */
    private Integer updateId;

    /**
     * 客户单位名称
     */
    private String name;

    /**
     * 客户地址
     */
    private String address;

    /**
     * 客户开户行
     */
    private String openBank;

    /**
     * 客户银行账号
     */
    private String bankAccount;

    /**
     * 客户类型-潜在、合作、大客户
     */
    private String type;

    /**
     * 客户所在地区
     */
    private String region;

    /**
     * 备注该客户
     */
    private String note;

    /**
     * 开拓渠道
     */
    private String channel;

    /**
     * 最后修改日期
     */
    private LocalDateTime updateDate;

    /**
     * 关系建立日期
     */
    private LocalDateTime createDate;


}
