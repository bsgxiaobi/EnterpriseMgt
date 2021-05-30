package com.bishugui.project.controller.customer;


import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.customer.CustomerVisit;
import com.bishugui.project.service.customer.CustomerVisitService;
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
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/customer-visit")
public class CustomerVisitController {
    @Autowired
    CustomerVisitService service;

    @GetMapping("/all-params")
    public Result getSelectResult(@RequestParam Map<String,Object> map){
        List<CustomerVisit> all = service.getSelectResult(map);
        return ResultUtils.success(all);
    }

    @GetMapping("/all")
    public Result getAll(){
        List<CustomerVisit> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam("cvId") Integer cvId){
        CustomerVisit visit = service.getOne(cvId);
        return ResultUtils.success(visit);
    }

    @PostMapping("insert-update")
    public Result insertOrUpdate(@RequestBody CustomerVisit customerVisit){
        CustomerVisit visit = service.insertOrUpdate(customerVisit);
        return ResultUtils.success(visit);
    }
}

