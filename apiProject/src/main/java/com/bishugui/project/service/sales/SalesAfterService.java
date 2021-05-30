package com.bishugui.project.service.sales;

import com.bishugui.project.pojo.sales.SalesAfter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
public interface SalesAfterService extends IService<SalesAfter> {
    List<SalesAfter> getAllAfter(Integer agent);

    SalesAfter getOneAfter(String saId);

    SalesAfter insertOrUpdateAfter(SalesAfter salesAfter);
}
