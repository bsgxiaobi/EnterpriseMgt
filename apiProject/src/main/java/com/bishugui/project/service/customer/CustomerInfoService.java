package com.bishugui.project.service.customer;

import com.bishugui.project.pojo.customer.CustomerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bishugui.project.pojo.personnel.Recruit;

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
public interface CustomerInfoService extends IService<CustomerInfo> {
    List<CustomerInfo> getSelectResult(Map<String,Object> map);
    List<CustomerInfo> getAllCustomerInfo();

    CustomerInfo getOneCustomerInfo(Integer cId);

    CustomerInfo insertOrUpdateCustomerInfo(CustomerInfo customerInfo);
}
