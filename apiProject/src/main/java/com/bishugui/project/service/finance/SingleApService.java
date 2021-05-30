package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.finance.SingleAp;
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
public interface SingleApService extends IService<SingleAp> {
    List<SingleAp> getAll();

    SingleAp getOne(String id);

    SingleAp insert(SingleAp singleAp);
}
