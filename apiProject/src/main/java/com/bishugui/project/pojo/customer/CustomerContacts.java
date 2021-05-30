package com.bishugui.project.pojo.customer;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
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
public class CustomerContacts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户单位联系人id-自增
     */
    @TableId(value = "cc_id", type = IdType.AUTO)
    private Integer ccId;

    /**
     * 客户单位id-customer表
     */
    private Integer cid;

    /**
     * 部门
     */
    private String department;

    /**
     * 职务
     */
    private String position;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 座机号码
     */
    private String landlineTell;

    /**
     * 手机号一
     */
    private String phoneOne;

    /**
     * 手机号二
     */
    private String phoneTwo;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * qq号码
     */
    private String qq;

    /**
     * 微信号
     */
    private String wchat;

    /**
     * 其他联系方式
     */
    private String otherTell;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 社会关系
     */
    private String socialRelations;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 更新人
     */
    private Integer updateId;

    /**
     * 创建日期
     */
    private LocalDateTime updateDate;

    private LocalDateTime createDate;


}
