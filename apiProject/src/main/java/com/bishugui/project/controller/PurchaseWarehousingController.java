package com.bishugui.project.controller;


import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.pojo.PurchaseWarehousing;
import com.bishugui.project.service.PurchaseWarehousingService;
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
@RequestMapping("/purchase-warehousing")
public class PurchaseWarehousingController {

    @Autowired
    PurchaseWarehousingService purchaseWarehousingService;

    @GetMapping("all")
    public Result getAllPurchaseWarehousing(@RequestParam(name = "employeeId", required = false) Integer employeeId){
        List<PurchaseWarehousing> list = purchaseWarehousingService.getAllPurchaseWarehousing(employeeId);
        return ResultUtils.success(list);
    }

    @PostMapping("insert-update")
    public  Result insertOrUpdateWarehousing(@RequestBody PurchaseWarehousing warehousing){
        PurchaseWarehousing purchaseWarehousing = purchaseWarehousingService.insertInquiryAndUpdateItem(warehousing);

        return ResultUtils.success(purchaseWarehousing);
    }
}

