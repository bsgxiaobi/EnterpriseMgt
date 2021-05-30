package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bishugui.project.mapper.PurchaseApplyMapper;
import com.bishugui.project.mapper.PurchaseItemMapper;
import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.pojo.PurchaseInquiry;
import com.bishugui.project.mapper.PurchaseInquiryMapper;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.service.PurchaseApplyService;
import com.bishugui.project.service.PurchaseInquiryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class PurchaseInquiryServiceImpl extends ServiceImpl<PurchaseInquiryMapper, PurchaseInquiry> implements PurchaseInquiryService {
    @Autowired
    PurchaseInquiryMapper purchaseInquiryMapper;
    @Autowired
    PurchaseApplyMapper purchaseApplyMapper;
    @Autowired
    PurchaseItemMapper purchaseItemMapper;

    @Override
    public List<PurchaseInquiry> getAllPurchaseInquiry(Integer agentId) {
        List<PurchaseInquiry> purchaseInquiryList = new ArrayList<>();

        //先查询出已经询价的采购单
        List<PurchaseInquiry> allPurchaseInquiry = purchaseInquiryMapper.getAllPurchaseInquiry(agentId);
        List<String> poIdList = new ArrayList<>();
        if(allPurchaseInquiry != null || allPurchaseInquiry.size() > 0){
            for (PurchaseInquiry purchaseInquiry : allPurchaseInquiry) {
                poIdList.add(purchaseInquiry.getPoId());
            }
//            List<PurchaseApply> applyList = purchaseApplyMapper.getAllPurchaseApplyById(poIdList);
//            for (int i = 0; i < applyList.size(); i++) {
//                allPurchaseInquiry.get(i).setPurchaseApply(applyList.get(i));
//            }
        }
        //再查询出没有询价的采购单
//        System.out.println("poIdList===========================");
//        System.out.println(poIdList.toString());
        List<PurchaseApply> purchaseApplyList = purchaseApplyMapper.getAllPurchaseApplyExcludePoId(poIdList);
        for (PurchaseApply purchaseApply : purchaseApplyList) {
            purchaseInquiryList.add(new PurchaseInquiry(purchaseApply));
        }


        //合并两个list
        purchaseInquiryList.addAll(allPurchaseInquiry);

        return purchaseInquiryList;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public PurchaseInquiry insertInquiryAndUpdateItem(PurchaseInquiry purchaseInquiry) {
        //设置经办日期
        purchaseInquiry.setAgentDate(LocalDateTime.now());
        //如果询价单的主键存在则转为更新
        if(purchaseInquiry.getPiId().equals("")){
            //插入询价单
            //生成主键
            String piId ="cx" + BasicUtils.generateBillPrimaryKey();
            purchaseInquiry.setPiId(piId);
            int insert = purchaseInquiryMapper.insert(purchaseInquiry);
        }else{
            //更新询价单
            int i = purchaseInquiryMapper.updateById(purchaseInquiry);
        }

        //更新采购清单
        List<PurchaseItem> itemList = purchaseInquiry.getPurchaseApply().getItemList();
        Integer i = purchaseItemMapper.updatePurchaseItemById(itemList);
        return purchaseInquiry;
    }

    @Override
    public PurchaseInquiry getOnePurchaseInquiryForPoId(String poId) {
        QueryWrapper<PurchaseInquiry> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("po_id",poId);
        PurchaseInquiry purchaseInquiry = purchaseInquiryMapper.selectOne(queryWrapper);
        return purchaseInquiry;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public String updateInquiryState(Map<String,String> map) {
        String nowTime = BasicUtils.getLocalDateTimeToString();
        map.put("approvalDate",nowTime);
        Integer state = purchaseInquiryMapper.updateInquiryState(map);

        return nowTime;
    }
}
