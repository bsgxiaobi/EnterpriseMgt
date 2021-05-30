package com.bishugui.project.service.customer;

import com.bishugui.project.pojo.customer.CustomerVisit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
public interface CustomerVisitService extends IService<CustomerVisit> {
    List<CustomerVisit> getSelectResult(Map<String,Object> map);

    List<CustomerVisit> getAll();

    CustomerVisit getOne(Integer cvId);

    CustomerVisit insertOrUpdate(CustomerVisit customerVisit);

}
