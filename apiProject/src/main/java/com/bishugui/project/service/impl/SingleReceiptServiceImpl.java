package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.mapper.finance.SingleArMapper;
import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.pojo.finance.SingleAr;
import com.bishugui.project.pojo.finance.SinglePayment;
import com.bishugui.project.pojo.finance.SingleReceipt;
import com.bishugui.project.mapper.finance.SingleReceiptMapper;
import com.bishugui.project.service.finance.SingleReceiptService;
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
public class SingleReceiptServiceImpl extends ServiceImpl<SingleReceiptMapper, SingleReceipt> implements SingleReceiptService {
    @Autowired
    SingleReceiptMapper mapper;
    @Autowired
    SingleArMapper singleArMapper;

    @Override
    public List<SingleReceipt> getAll(String id) {
        QueryWrapper<SingleReceipt> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sar_id",id);
        List<SingleReceipt> list = mapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public SingleReceipt insert(SingleReceipt singleReceipt) {
        String id ="sk" + BasicUtils.generateBillPrimaryKey();
        singleReceipt.setSrId(id);
        singleReceipt.setCreateDate(LocalDateTime.now());
        int insert = mapper.insert(singleReceipt);
        //更新应收单的已收金额
        //查出应收单的已收金额
        SingleAr singleAr = singleArMapper.selectById(singleReceipt.getSarId());
        singleAr.setReceived(singleAr.getReceived() + singleReceipt.getTotal());
        singleArMapper.updateById(singleAr);
        return singleReceipt;
    }
}
