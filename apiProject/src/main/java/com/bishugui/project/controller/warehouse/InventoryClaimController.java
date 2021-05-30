package com.bishugui.project.controller.warehouse;


import com.bishugui.project.pojo.sales.SalesQuotation;
import com.bishugui.project.pojo.warehouse.InventoryClaim;
import com.bishugui.project.service.warehouse.InventoryClaimService;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/inventory-claim")
public class InventoryClaimController {
    @Autowired
    InventoryClaimService inventoryClaimService;

    @GetMapping("/all")
    public Result getAllSalesQuotation(@RequestParam(name="applyId",required = false) Integer applyId){
        List<InventoryClaim> allInventoryClaim = inventoryClaimService.getAllInventoryClaim(applyId);
        return ResultUtils.success(allInventoryClaim);
    }

    @GetMapping("/one")
    public Result getOneInventoryClaim(@RequestParam("claimId") String claimId){
        InventoryClaim inventoryClaim = inventoryClaimService.getOneInventoryClaim(claimId);
        return ResultUtils.success(inventoryClaim);
    }

    @PostMapping("/insert-update")
    public Result insertOrUpdate(@RequestBody InventoryClaim inventoryClaim){
        InventoryClaim claim = inventoryClaimService.insertOrUpdateInventoryClaim(inventoryClaim);
        return ResultUtils.success(claim);
    }

    @PostMapping("/approval")
    public Result approvalInventoryClaim(@RequestBody InventoryClaim inventoryClaim){
        String s = inventoryClaimService.approvalInventoryClaim(inventoryClaim);
        return ResultUtils.success(s);
    }
}

