package com.bishugui.project.controller;


import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.service.PurchaseApplyService;
import com.bishugui.project.service.PurchaseInquiryService;
import com.bishugui.project.service.impl.PurchaseApplyServiceImpl;
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
@RequestMapping(path = "/purchase-apply")
public class PurchaseApplyController {
    @Autowired
    PurchaseApplyService purchaseApplyService;

    @PostMapping("/update-state")
    public Result updateState(@RequestBody Map<String, String> params){
        String approvalDate = purchaseApplyService.updateAuditState(params);
//        return ResultUtils.success(params);
        if(approvalDate != null)  return ResultUtils.success(approvalDate);
        else return ResultUtils.fail("失败");
    }
    @GetMapping("/all-purchase-apply")
    public Result getAllPurchaseApply(@RequestParam(name = "state",required = false) String state,
                                      @RequestParam(name = "employeeId", required = false) String employeeId){
        System.out.println("state:"+state);
        System.out.println("employeeId:"+employeeId);
        Map<String,String> map = new HashMap<>();
        map.put("state",state);
        map.put("employeeId",employeeId);
        List<PurchaseApply> purchaseApplyList = purchaseApplyService.getAllPurchaseApply(map);
        purchaseApplyList.forEach(System.out::println);
        return ResultUtils.success(purchaseApplyList);
    }

    @PostMapping("/add")
    public Result addData(@RequestBody PurchaseApply purchaseApply){
        System.out.println(purchaseApply.toString());

        //插入到数据库
        Integer insertResult = purchaseApplyService.insertPurchaseApply(purchaseApply);
        if(insertResult == 1) return ResultUtils.success(purchaseApply);
        else return ResultUtils.fail("添加采购申请单失败");
    }

}

