package com.bishugui.project.service;

import com.bishugui.project.pojo.PurchaseItem;
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
public interface PurchaseItemService extends IService<PurchaseItem> {
    //根据采购申请单号获取采购清单列表
    public List<PurchaseItem> getPurchaseItemListById(String poId);

    //根据采购申请单号批量更新采购询价单价与金额
    public Boolean updateInquiryById(List<PurchaseItem> list);

}
