package com.bishugui.project.pojo.finance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

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
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<VoucherItem> voucherItemList;
    /**
     * 记账凭证id-自增
     */
    @TableId(value = "bv_id", type = IdType.AUTO)
    private Integer bvId;

    private String bvAbstracts;
    /**
     * 凭证字
     */
    private String voucherWord;

    /**
     * 附件数
     */
    private Integer attachmentNum;

    private Double total;

    private Integer createId;

    private String createName;

    private LocalDateTime createDate;

    /**
     * 审核人
     */
    private Integer reviewId;

    private String reviewName;

    /**
     * 审核日期
     */
    private LocalDateTime reviewDate;

    /**
     * 记账人id
     */
    private Integer bookkeepId;

    private String bookkeepName;

    private LocalDateTime bookkeepDate;

    private String state;

}
