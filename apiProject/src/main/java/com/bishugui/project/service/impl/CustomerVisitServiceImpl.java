package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.customer.CustomerVisit;
import com.bishugui.project.mapper.customer.CustomerVisitMapper;
import com.bishugui.project.service.customer.CustomerVisitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
@Service
public class CustomerVisitServiceImpl extends ServiceImpl<CustomerVisitMapper, CustomerVisit> implements CustomerVisitService {
    @Autowired
    CustomerVisitMapper customerVisitMapper;

    @Override
    public List<CustomerVisit> getSelectResult(Map<String, Object> map) {
        if(map.get("name") == null || map.get("name") == ""){
            map.put("name",null);
        }
        if(map.get("title") == null || map.get("title") == ""){
            map.put("title",null);
        }
        if(map.get("startDate") == null || map.get("startDate") == ""){
            map.put("startDate",null);
            map.put("endDate",null);
        }

        System.out.println("map::::");
        System.out.println(map.toString());
        if(map.get("title") == null) System.out.println("title===null");
        List<CustomerVisit> all = customerVisitMapper.getSelectResult(map);
        return all;
    }

    @Override
    public List<CustomerVisit> getAll() {
        List<CustomerVisit> all = customerVisitMapper.getAll(null);
        return all;
    }

    @Override
    public CustomerVisit getOne(Integer cvId) {
        List<CustomerVisit> all = customerVisitMapper.getAll(cvId);
        return all.get(0);
    }

    @Override
    public CustomerVisit insertOrUpdate(CustomerVisit customerVisit) {
        customerVisit.setAgentDate(LocalDateTime.now());
        if(customerVisit.getCvId() != null && customerVisit.getCvId() > 0){
            int i = customerVisitMapper.updateById(customerVisit);
        }else{
            customerVisit.setVisitDate(LocalDateTime.now());
            int insert = customerVisitMapper.insert(customerVisit);
        }
        return customerVisit;
    }
}
