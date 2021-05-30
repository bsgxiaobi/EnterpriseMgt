package com.bishugui.project.service;

import com.bishugui.project.pojo.PurchaseInquiry;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
public interface PurchaseInquiryService extends IService<PurchaseInquiry> {
    //获取所有采购询价单
    List<PurchaseInquiry> getAllPurchaseInquiry(Integer agentId);

    //插入采购询价单和更新采购清单，返回当前插入或者更新的询价单
    PurchaseInquiry insertInquiryAndUpdateItem(PurchaseInquiry purchaseInquiry);

    //根据采购申请单号获取一条采购询价单
    PurchaseInquiry getOnePurchaseInquiryForPoId(String poId);

    //变更审核状态,返回审核时间
    String updateInquiryState(Map<String,String> map);
}
