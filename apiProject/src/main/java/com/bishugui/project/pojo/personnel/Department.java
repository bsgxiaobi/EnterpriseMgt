package com.bishugui.project.pojo.personnel;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2021-05-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增部门id
     */
    @TableId(value = "department_id", type = IdType.AUTO)
    private Integer departmentId;

    /**
     * 部门名称
     */
    private String title;

    /**
     * 部门描述
     */
    private String description;

    private LocalDateTime updateDate;

    /**
     * 更新操作人
     */
    private Integer agentId;


}
