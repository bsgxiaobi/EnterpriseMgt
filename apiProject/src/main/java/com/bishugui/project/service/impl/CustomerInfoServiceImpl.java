package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.mapper.customer.CustomerInfoMapper;
import com.bishugui.project.pojo.personnel.Recruit;
import com.bishugui.project.service.customer.CustomerInfoService;
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
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

    @Autowired
    CustomerInfoMapper customerInfoMapper;


    @Override
    public List<CustomerInfo> getSelectResult(Map<String, Object> map) {
        QueryWrapper<CustomerInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",map.get("name"));
        if(map.get("startDate") != null && map.get("startDate") != ""){
            queryWrapper.between("apply_date",map.get("startDate"),map.get("endDate"));
        }
        return customerInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<CustomerInfo> getAllCustomerInfo() {
        List<CustomerInfo> infoList = customerInfoMapper.selectList(null);
        return infoList;
    }

    @Override
    public CustomerInfo getOneCustomerInfo(Integer cid) {
        CustomerInfo customerInfo = customerInfoMapper.selectById(cid);
        return customerInfo;
    }

    @Override
    public CustomerInfo insertOrUpdateCustomerInfo(CustomerInfo customerInfo) {
        customerInfo.setUpdateDate(LocalDateTime.now());
        if(customerInfo.getCid() != null && customerInfo.getCid() > 0) {
            int i = customerInfoMapper.updateById(customerInfo);
        }else{
            customerInfo.setCreateDate(LocalDateTime.now());
            int i = customerInfoMapper.insert(customerInfo);
        }
        return customerInfo;
    }
}
