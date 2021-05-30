package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bishugui.project.mapper.finance.SingleApMapper;
import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.pojo.finance.SinglePayment;
import com.bishugui.project.mapper.finance.SinglePaymentMapper;
import com.bishugui.project.service.finance.SinglePaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class SinglePaymentServiceImpl extends ServiceImpl<SinglePaymentMapper, SinglePayment> implements SinglePaymentService {
    @Autowired
    SinglePaymentMapper mapper;
    @Autowired
    SingleApMapper singleApMapper;

    @Override
    public List<SinglePayment> getAll(String id) {
        QueryWrapper<SinglePayment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sap_id",id);
        List<SinglePayment> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public SinglePayment insert(SinglePayment singlePayment) {
        String id ="fk" + BasicUtils.generateBillPrimaryKey();
        singlePayment.setSpId(id);
        singlePayment.setCreateDate(LocalDateTime.now());
        int insert = mapper.insert(singlePayment);
        //更新应付单的已付金额
        //查出应付单的已付金额
        SingleAp singleAp = singleApMapper.selectById(singlePayment.getSapId());
        singleAp.setPaid(singleAp.getPaid() + singlePayment.getTotal());
        singleApMapper.updateById(singleAp);
        return singlePayment;
    }
}
