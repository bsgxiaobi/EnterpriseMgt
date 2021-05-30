package com.bishugui.project.pojo.finance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-05-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class VoucherItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记账凭证内容项目id-自增
     */
    @TableId(value = "vi_id", type = IdType.AUTO)
    private Integer viId;

    /**
     * 记账凭证id-voucher_bookkeeping表
     */
    private Integer bvId;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 总账科目
     */
    private String ledger;

    /**
     * 明细科目
     */
    private String detailed;

    /**
     * 会计科目id
     */
    private Integer asId;

    /**
     * 会计科目名称
     */
    private String name;

    /**
     * 借方金额
     */
    private Double borrow;

    /**
     * 贷方金额
     */
    private Double credit;


}
