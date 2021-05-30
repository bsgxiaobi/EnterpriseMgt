package com.bishugui.project.service.impl;

import com.bishugui.project.mapper.customer.CustomerInfoMapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.pojo.finance.SingleAr;
import com.bishugui.project.mapper.finance.SingleArMapper;
import com.bishugui.project.service.finance.SingleArService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
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
 * @since 2021-05-24
 */
@Service
public class SingleArServiceImpl extends ServiceImpl<SingleArMapper, SingleAr> implements SingleArService {
    @Autowired
    SingleArMapper mapper;
    @Autowired
    CustomerInfoMapper customerInfoMapper;

    @Override
    public List<SingleAr> getAll() {
        List<SingleAr> all = mapper.getAll(null);
        return all;
    }

    @Override
    public SingleAr getOne(String id) {
        List<SingleAr> all= mapper.getAll(id);
        return all.get(0);
    }

    @Override
    public SingleAr insert(SingleAr singleAr) {
        String id ="ys" + BasicUtils.generateBillPrimaryKey();
        singleAr.setSarId(id);
        singleAr.setCreateDate(LocalDateTime.now());
        int insert = mapper.insert(singleAr);
        //查询出关联的客户名称
        CustomerInfo customerInfo = customerInfoMapper.selectById(singleAr.getCid());
        singleAr.setCustomerInfo(customerInfo);
        return singleAr;
    }
}
