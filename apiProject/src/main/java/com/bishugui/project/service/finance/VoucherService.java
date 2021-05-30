package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.finance.Voucher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
public interface VoucherService extends IService<Voucher> {
    public List<Voucher> getAll();

    public List<Voucher> getAllReview();

    public List<Voucher> getAllBookkeeping();

    public Voucher insert(Voucher voucher);

    public String reviewData(List<Voucher> list);

    public String bookkeepData(List<Voucher> list);
}
