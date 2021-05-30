package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.mapper.PurchaseItemMapper;
import com.bishugui.project.service.PurchaseItemService;
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
 * @since 2021-05-05
 */
@Service
public class PurchaseItemServiceImpl extends ServiceImpl<PurchaseItemMapper, PurchaseItem> implements PurchaseItemService {

    @Autowired
    PurchaseItemMapper purchaseItemMapper;

    @Override
    public List<PurchaseItem> getPurchaseItemListById(String poId) {
        QueryWrapper<PurchaseItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("po_id",poId);
        List<PurchaseItem> itemList = purchaseItemMapper.selectList(queryWrapper);

        return itemList;
    }

    @Override
    public Boolean updateInquiryById(List<PurchaseItem> list) {
        Integer i = purchaseItemMapper.updatePurchaseItemById(list);
        if (i>0) return true;
        else return false;
    }
}
