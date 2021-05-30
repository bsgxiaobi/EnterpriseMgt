package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.pojo.finance.SingleAr;
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
public interface SingleArService extends IService<SingleAr> {
    List<SingleAr> getAll();

    SingleAr getOne(String id);

    SingleAr insert(SingleAr singleAr);
}
