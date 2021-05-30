package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.mapper.customer.CustomerInfoMapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.mapper.finance.SingleApMapper;
import com.bishugui.project.service.finance.SingleApService;
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
public class SingleApServiceImpl extends ServiceImpl<SingleApMapper, SingleAp> implements SingleApService {
    @Autowired
    SingleApMapper mapper;
    @Autowired
    CustomerInfoMapper customerInfoMapper;

    @Override
    public List<SingleAp> getAll() {
        List<SingleAp> singleAps = mapper.getAll(null);
        return singleAps;
    }

    @Override
    public SingleAp getOne(String id) {
        List<SingleAp> singleAps = mapper.getAll(id);
        return singleAps.get(0);
    }

    @Override
    public SingleAp insert(SingleAp singleAp) {
        String id ="yf" + BasicUtils.generateBillPrimaryKey();
        singleAp.setSapId(id);
        singleAp.setCreateDate(LocalDateTime.now());
        int insert = mapper.insert(singleAp);
        //查询出关联的客户名称
        CustomerInfo customerInfo = customerInfoMapper.selectById(singleAp.getCid());
        singleAp.setCustomerInfo(customerInfo);
        return singleAp;
    }
}
