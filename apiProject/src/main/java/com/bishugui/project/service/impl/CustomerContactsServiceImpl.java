package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.customer.CustomerContacts;
import com.bishugui.project.mapper.customer.CustomerContactsMapper;
import com.bishugui.project.service.customer.CustomerContactsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
@Service
public class CustomerContactsServiceImpl extends ServiceImpl<CustomerContactsMapper, CustomerContacts> implements CustomerContactsService {
    @Autowired
    CustomerContactsMapper contactsMapper;

    @Override
    public List<CustomerContacts> getAllCustomerContacts(Integer cid) {
        System.out.println("cid::" + cid);
        QueryWrapper<CustomerContacts> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cid",cid);
        List<CustomerContacts> contactsList = contactsMapper.selectList(queryWrapper);
        return contactsList;
    }

    @Override
    public CustomerContacts getOneCustomerContacts(Integer ccId) {
        CustomerContacts customerContacts = contactsMapper.selectById(ccId);
        return customerContacts;
    }

    @Override
    public Boolean deleteOneContact(Integer ccId) {
        int i = contactsMapper.deleteById(ccId);
        if(i > 0) return true;
        else return false;
    }

    @Override
    public CustomerContacts insertOrUpdateContact(CustomerContacts contacts) {
        contacts.setUpdateDate(LocalDateTime.now());
        if(contacts.getCcId() != null && contacts.getCcId() > 0){
            int i = contactsMapper.updateById(contacts);
        }else{
            contacts.setCreateDate(LocalDateTime.now());
            int insert = contactsMapper.insert(contacts);
        }
        return contacts;
    }
}
