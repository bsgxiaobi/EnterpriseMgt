package com.bishugui.project.pojo.finance;

import java.io.Serializable;

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
 * @since 2021-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AccountingSubjects implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Double borrowSum;
    @TableField
    private Double creditSum;

    @TableId
    private Integer id;

    /**
     * 会计科目id
     */
    private Integer asId;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 适用范围
     */
    private String range;

    /**
     * 科目类型
     */
    private String type;


}
