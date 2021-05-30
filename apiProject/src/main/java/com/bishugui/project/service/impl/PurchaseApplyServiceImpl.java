package com.bishugui.project.service.impl;

import com.bishugui.project.mapper.PurchaseItemMapper;
import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.mapper.PurchaseApplyMapper;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.service.PurchaseApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Service
public class PurchaseApplyServiceImpl extends ServiceImpl<PurchaseApplyMapper, PurchaseApply> implements PurchaseApplyService {

    @Autowired
    PurchaseApplyMapper purchaseApplyMapper;

    @Autowired
    PurchaseItemMapper purchaseItemMapper;

    @Override
    public List<PurchaseApply> getAllPurchaseApply(Map<String,String> map) {
        return  purchaseApplyMapper.getAllPurchaseApply(map);
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public Integer insertPurchaseApply(PurchaseApply purchaseApply) {
        //设置当前的申请单主键
        String poId ="cs" + BasicUtils.generateBillPrimaryKey();
        purchaseApply.setPoId(poId);
        //设置插入日期
        purchaseApply.setApplyDate(LocalDateTime.now());
        int insertResult = purchaseApplyMapper.insert(purchaseApply);
        System.out.println("插入申请单：" + insertResult);
        for (PurchaseItem purchaseItem:purchaseApply.getItemList()) {
//            System.out.println(purchaseItem.toString());
            purchaseItem.setPoId(poId);
            int i = purchaseItemMapper.insert(purchaseItem);
            System.out.println("插入采购清单项：" + i);
        }
        return 1;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public String updateAuditState(Map<String, String> map) {
        map.put("approvalDate",BasicUtils.getLocalDateTimeToString());
        System.out.println("插入时间："+map.get("approvalDate"));
        Integer result = purchaseApplyMapper.updateAuditState(map);
        return map.get("approvalDate");
    }


}
