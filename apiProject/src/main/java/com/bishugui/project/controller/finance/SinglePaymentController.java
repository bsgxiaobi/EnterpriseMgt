package com.bishugui.project.controller.finance;


import com.bishugui.project.pojo.customer.CustomerContacts;
import com.bishugui.project.pojo.finance.SinglePayment;
import com.bishugui.project.service.customer.CustomerContactsService;
import com.bishugui.project.service.finance.SinglePaymentService;
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
 * @since 2021-05-24
 */
@RestController
@RequestMapping("/single-payment")
public class SinglePaymentController {
    @Autowired
    SinglePaymentService service;

    @GetMapping("all")
    public Result getAll(@RequestParam("id") String id){
        List<SinglePayment> all = service.getAll(id);
        return ResultUtils.success(all);
    }

    @PostMapping("insert")
    public Result insert(@RequestBody SinglePayment singlePayment){
        SinglePayment insert = service.insert(singlePayment);
        return ResultUtils.success(insert);
    }
}

