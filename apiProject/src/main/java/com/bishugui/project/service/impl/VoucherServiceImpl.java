package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.mapper.finance.VoucherItemMapper;
import com.bishugui.project.pojo.finance.Voucher;
import com.bishugui.project.mapper.finance.VoucherMapper;
import com.bishugui.project.pojo.finance.VoucherItem;
import com.bishugui.project.service.finance.VoucherService;
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
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements VoucherService {
    @Autowired
    VoucherMapper mapper;
    @Autowired
    VoucherItemMapper voucherItemMapper;

    @Override
    public List<Voucher> getAll() {
        QueryWrapper<Voucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_date");
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Voucher> getAllReview() {
        QueryWrapper<Voucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("bookkeep_id");
        queryWrapper.orderByDesc("create_date");
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Voucher> getAllBookkeeping() {
        QueryWrapper<Voucher> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("review_id");
        queryWrapper.ne("state","审核未通过");
        queryWrapper.orderByDesc("review_date");
        return mapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public Voucher insert(Voucher voucher) {
        voucher.setCreateDate(LocalDateTime.now());
        int i = mapper.insert(voucher);
        System.out.println(voucher.toString());
        for(VoucherItem item : voucher.getVoucherItemList()){
            item.setBvId(voucher.getBvId());
            System.out.println(item.toString());
            voucherItemMapper.insert(item);
        }
        return voucher;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public String reviewData(List<Voucher> list) {
        LocalDateTime time = LocalDateTime.now();
        for(int i=0;i<list.size();i++){
            list.get(i).setReviewDate(time);
            mapper.updateById(list.get(i));
        }
        return BasicUtils.getAppointLocalDateTimeToString(time);
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public String bookkeepData(List<Voucher> list) {
        LocalDateTime time = LocalDateTime.now();
        for(int i=0;i<list.size();i++){
            list.get(i).setBookkeepDate(time);
            mapper.updateById(list.get(i));
        }
        return BasicUtils.getAppointLocalDateTimeToString(time);
    }

}
