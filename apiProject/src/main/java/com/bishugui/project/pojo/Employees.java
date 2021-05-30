package com.bishugui.project.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.pojo.personnel.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Employees implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Position position;

    @TableField(exist = false)
    private Department department;

    @TableId(type = IdType.AUTO)
    private Integer employeeId;

    private Integer departmentId;
    private String positionId;
    private String name;
    private String sex;
    private String phone;
    private String email;
    private String pwd;
    private String national;
    private String cardId;
}
