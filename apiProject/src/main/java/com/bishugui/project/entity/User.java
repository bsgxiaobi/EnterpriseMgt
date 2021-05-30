package com.bishugui.project.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class User {
    /*AUTO(0),数据库id自增
    NONE(1),未设置id
    INPUT(2),手动输入
    ASSIGN_ID(3),默认的全局唯一id
    ASSIGN_UUID(4),全局唯一uuid*/
    //对应数据库中的主键
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private Integer age;
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
}
