package com.bishugui.project.controller;


import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.pojo.PurchaseInquiry;
import com.bishugui.project.service.PurchaseApplyService;
import com.bishugui.project.service.PurchaseInquiryService;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@RestController
@RequestMapping("/purchase-inquiry")
public class PurchaseInquiryController {
    @Autowired
    PurchaseInquiryService purchaseInquiryService;
    @Autowired
    PurchaseApplyService purchaseApplyService;

    //拉取所有询价单信息
    @GetMapping("/all-purchase-inquiry")
    public Result getAllPurchaseInquiry(@RequestParam(name = "employeeId", required = false) Integer employeeId){
        System.out.println("employeeId:"+employeeId);
        List<PurchaseInquiry> purchaseInquiryList = purchaseInquiryService.getAllPurchaseInquiry(employeeId);

        return ResultUtils.success(purchaseInquiryList);
    }

    //插入或者更新采购询价单信息
    @PostMapping("/insert-update")
    public Result updatePurchaseInquiry(@RequestBody PurchaseInquiry purchaseInquiry){
        PurchaseInquiry result = purchaseInquiryService.insertInquiryAndUpdateItem(purchaseInquiry);
//        System.out.println(purchaseInquiry.toString());
        if(result != null) return ResultUtils.success(result);
        return ResultUtils.fail("失败");
    }

    //根据采购申请单id查询出单个采购询价单
    @GetMapping("/one")
    public Result getOnePurchaseInquiryForPoId(@RequestParam("poId") String poId){
        PurchaseInquiry onePurchaseInquiryForPoId = purchaseInquiryService.getOnePurchaseInquiryForPoId(poId);
        return ResultUtils.success(onePurchaseInquiryForPoId);
    }

    //变更询价单审核状态
    @PostMapping("/update-state")
    public Result updateInquiryState(@RequestBody Map<String,String> map){
        String nowTime = purchaseInquiryService.updateInquiryState(map);
        return ResultUtils.success(nowTime);
    }
}

