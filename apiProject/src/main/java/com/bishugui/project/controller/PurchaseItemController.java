package com.bishugui.project.controller;


import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.service.PurchaseItemService;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/purchase-item")
public class PurchaseItemController {

    @Autowired
    PurchaseItemService purchaseItemService;

    @GetMapping("/current-item-list")
    public Result selectItemList(String poId){
        System.out.println(poId);
        List<PurchaseItem> purchaseItemList = purchaseItemService.getPurchaseItemListById(poId);
        return ResultUtils.success(purchaseItemList);
    }
}

