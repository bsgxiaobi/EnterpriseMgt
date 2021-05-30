package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.mapper.sales.SalesItemMapper;
import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.pojo.warehouse.ClaimItem;
import com.bishugui.project.mapper.warehouse.ClaimItemMapper;
import com.bishugui.project.service.warehouse.ClaimItemService;
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
 * @since 2021-05-18
 */
@Service
public class ClaimItemServiceImpl extends ServiceImpl<ClaimItemMapper, ClaimItem> implements ClaimItemService {
    @Autowired
    ClaimItemMapper claimItemMapper;

    @Override
    public List<ClaimItem> getAllByClaimId(String claimId) {
        QueryWrapper<ClaimItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("claim_id",claimId);
        List<ClaimItem> itemList = claimItemMapper.selectList(queryWrapper);
        return itemList;
    }
}
