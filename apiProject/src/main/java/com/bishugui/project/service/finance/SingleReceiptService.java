package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.finance.SinglePayment;
import com.bishugui.project.pojo.finance.SingleReceipt;
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
public interface SingleReceiptService extends IService<SingleReceipt> {
    List<SingleReceipt> getAll(String id);

    SingleReceipt insert(SingleReceipt singleReceipt);
}
