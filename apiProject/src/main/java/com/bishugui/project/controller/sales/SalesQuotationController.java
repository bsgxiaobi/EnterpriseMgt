package com.bishugui.project.controller.sales;


import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.pojo.sales.SalesQuotation;
import com.bishugui.project.service.sales.SalesItemService;
import com.bishugui.project.service.sales.SalesQuotationService;
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
 * @since 2021-05-09
 */
@RestController
@RequestMapping("/sales-quotation")
public class SalesQuotationController {
    @Autowired
    SalesQuotationService salesQuotationService;
    @Autowired
    SalesItemService salesItemService;

    @GetMapping("/item")
    public Result getItemBySqId(@RequestParam("sqId") String sqId){
        List<SalesItem> itemList = salesItemService.getAllSalesItemBySqId(sqId);
        return ResultUtils.success(itemList);
    }
    @GetMapping("/all")
    public Result getAllSalesQuotation(@RequestParam(name="agent",required = false) Integer agent,
                                       @RequestParam(name="state",required = false) String state){
        List<SalesQuotation> quotationList = salesQuotationService.getAllSalesQuotation(agent,state);
        return ResultUtils.success(quotationList);
    }

    @GetMapping("/all-delivery")
    public Result getAllDeliveryQuotation(@RequestParam(name="agent",required = false) Integer agent){
        List<SalesQuotation> quotationList = salesQuotationService.getAllDeliveryQuotation(agent);
        return ResultUtils.success(quotationList);
    }

    @GetMapping("/one")
    public Result getOneSalesQuotation(@RequestParam("sqId") String sqId){
        SalesQuotation quotation = salesQuotationService.getOneSalesQuotation(sqId);
        return ResultUtils.success(quotation);
    }

    @PostMapping("/insert-update")
    public Result insertOrUpdate(@RequestBody SalesQuotation salesQuotation){
        SalesQuotation quotation = salesQuotationService.insertOrUpdateSalesQuotation(salesQuotation);
        return ResultUtils.success(salesQuotation);
    }

    @PostMapping("/update-state")
    public Result updateQuotationState(@RequestBody Map<String,String> map){
        Boolean bl = salesQuotationService.updateState(map);
        if(bl) return ResultUtils.success(null);
        else return ResultUtils.fail("失败");
    }

    @PostMapping("/delivery")
    public Result deliveryEvent(@RequestBody SalesQuotation salesQuotation){
        SalesQuotation quotation = salesQuotationService.deliveryGoods(salesQuotation);
        if(quotation != null) return ResultUtils.success(quotation);
        else return ResultUtils.fail("失败");
    }
}

