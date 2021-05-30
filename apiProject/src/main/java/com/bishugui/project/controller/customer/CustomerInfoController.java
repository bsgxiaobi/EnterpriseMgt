package com.bishugui.project.controller.customer;


import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.service.customer.CustomerInfoService;
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
@RequestMapping("/customer-info")
public class CustomerInfoController {
    @Autowired
    CustomerInfoService customerInfoService;

    @GetMapping("/all-params")
    public Result getSelectResult(@RequestParam Map<String,Object> map){
        List<CustomerInfo> allCustomerInfo = customerInfoService.getSelectResult(map);
        return ResultUtils.success(allCustomerInfo);
    }
    @GetMapping("/all")
    public Result getAllCustomerInfo(){
        List<CustomerInfo> allCustomerInfo = customerInfoService.getAllCustomerInfo();
        return ResultUtils.success(allCustomerInfo);
    }

    @GetMapping("/one")
    public Result getOneCustomerInfo(@RequestParam("cid") Integer cid){
        CustomerInfo oneCustomerInfo = customerInfoService.getOneCustomerInfo(cid);
        return ResultUtils.success(oneCustomerInfo);
    }

    @PostMapping("/insert-update")
    public Result insertOrUpdateCustomerInfo(@RequestBody CustomerInfo customerInfo){
        CustomerInfo info = customerInfoService.insertOrUpdateCustomerInfo(customerInfo);
        return ResultUtils.success(info);
    }
}

