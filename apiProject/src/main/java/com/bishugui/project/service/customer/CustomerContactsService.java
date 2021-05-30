package com.bishugui.project.service.customer;

import com.bishugui.project.pojo.customer.CustomerContacts;
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
public interface CustomerContactsService extends IService<CustomerContacts> {
    List<CustomerContacts> getAllCustomerContacts(Integer cid);

    CustomerContacts getOneCustomerContacts(Integer ccId);

    Boolean deleteOneContact(Integer ccId);

    CustomerContacts insertOrUpdateContact(CustomerContacts contacts);
}
