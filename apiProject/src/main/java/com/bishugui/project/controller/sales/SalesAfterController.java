package com.bishugui.project.controller.sales;


import com.bishugui.project.pojo.sales.SalesAfter;
import com.bishugui.project.pojo.sales.SalesQuotation;
import com.bishugui.project.service.sales.SalesAfterService;
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
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/sales-after")
public class SalesAfterController {
    @Autowired
    SalesAfterService salesAfterService;

    //拉取所有售后单
    @GetMapping("/all")
    public Result getAllSalesAlter(@RequestParam(name="agent",required = false) Integer agent){
        List<SalesAfter> allAfter = salesAfterService.getAllAfter(agent);
        return ResultUtils.success(allAfter);
    }

    //根据主键值拉取所有售后单
    @GetMapping("/one")
    public Result getOneSalesAlter(@RequestParam("saId") String saId){
        SalesAfter after = salesAfterService.getOneAfter(saId);
        return ResultUtils.success(after);
    }

    //插入或者更新售后单
    @PostMapping("insert-update")
    public Result insertOrUpdate(@RequestBody SalesAfter salesAfter){
        SalesAfter after = salesAfterService.insertOrUpdateAfter(salesAfter);
        return ResultUtils.success(after);
    }
}

