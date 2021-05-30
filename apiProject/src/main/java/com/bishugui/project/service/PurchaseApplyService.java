package com.bishugui.project.service;

import com.bishugui.project.pojo.PurchaseApply;
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
public interface PurchaseApplyService extends IService<PurchaseApply> {
    //查询所有采购申请单列表
    public List<PurchaseApply> getAllPurchaseApply(Map<String,String> map);

    //插入一天采购申请单和申请单详情多条列表
    public Integer insertPurchaseApply(PurchaseApply purchaseApply);

    //变更审核状态
    public String updateAuditState(Map<String,String> map);
}
