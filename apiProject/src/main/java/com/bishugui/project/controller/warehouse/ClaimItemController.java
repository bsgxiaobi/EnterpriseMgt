package com.bishugui.project.controller.warehouse;


import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.pojo.sales.SalesQuotation;
import com.bishugui.project.pojo.warehouse.ClaimItem;
import com.bishugui.project.pojo.warehouse.InventoryClaim;
import com.bishugui.project.service.warehouse.ClaimItemService;
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
@RequestMapping("/claim-item")
public class ClaimItemController {
    @Autowired
    ClaimItemService claimItemService;

    @GetMapping("/item")
    public Result getItemBySqId(@RequestParam("claimId") String claimId){
        List<ClaimItem> itemList = claimItemService.getAllByClaimId(claimId);
        return ResultUtils.success(itemList);
    }

}

