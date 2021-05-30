package com.bishugui.project.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

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
 * @since 2021-05-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AttendanceRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Employees employees;

    /**
     * 考勤主键-自增
     */
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 关联员工
     */
    private Integer employeeId;

    /**
     * 类型
     */
    private String type;

    /**
     * 打卡机器
     */
    private String equipment;

    private LocalDateTime updateDate;

    /**
     * 申述
     */
    private String represent;
    /**
     * 申述状态
     */
    private String representState;

    private Integer agentId;

    private LocalDateTime representDate;

    private LocalDateTime agentDate;


}
