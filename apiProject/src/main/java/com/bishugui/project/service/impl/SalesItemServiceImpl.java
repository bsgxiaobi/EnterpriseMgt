package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.mapper.sales.SalesItemMapper;
import com.bishugui.project.service.sales.SalesItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
@Service
public class SalesItemServiceImpl extends ServiceImpl<SalesItemMapper, SalesItem> implements SalesItemService {
    @Autowired
    SalesItemMapper salesItemMapper;
    @Override
    public List<SalesItem> getAllSalesItemBySqId(String sqId) {
        QueryWrapper<SalesItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sq_id",sqId);
        List<SalesItem> salesItemList = salesItemMapper.selectList(queryWrapper);
        return salesItemList;
    }
}
