package com.bishugui.project.service;

import com.bishugui.project.pojo.PurchaseInquiry;
import com.bishugui.project.pojo.PurchaseWarehousing;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
public interface PurchaseWarehousingService extends IService<PurchaseWarehousing> {
    //获取所有入库表
    List<PurchaseWarehousing> getAllPurchaseWarehousing(Integer agentId);

    //插入采购入库单和更新采购清单，返回当前插入或者更新的入库单
    PurchaseWarehousing insertInquiryAndUpdateItem(PurchaseWarehousing purchaseWarehousing);

}
