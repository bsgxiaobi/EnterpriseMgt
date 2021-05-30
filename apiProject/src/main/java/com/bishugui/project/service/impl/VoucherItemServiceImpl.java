package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.finance.VoucherItem;
import com.bishugui.project.mapper.finance.VoucherItemMapper;
import com.bishugui.project.service.finance.VoucherItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
@Service
public class VoucherItemServiceImpl extends ServiceImpl<VoucherItemMapper, VoucherItem> implements VoucherItemService {
    @Autowired
    VoucherItemMapper mapper;

    @Override
    public List<VoucherItem> getAll(Integer id) {
        QueryWrapper<VoucherItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bv_id",id);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<VoucherItem> getAllByParams(Map<String, Object> map) {
        //QueryWrapper<VoucherItem> queryWrapper = new QueryWrapper<>();
        //传入了科目明细
//        if(map.get("asId") != null){
//            queryWrapper.eq("as_id",map.get("asId"));
//        }
        System.out.println("map::::::::::::");
        System.out.println(map.toString());
        if(map.get("startDate") == ""){
            System.out.println("startDate == null");
            map.put("startDate",null);
            map.put("endDate",null);
        }
        System.out.println(map.toString());
        List<VoucherItem> itemList = mapper.getAllByParams(map);
        return itemList;
    }
}
