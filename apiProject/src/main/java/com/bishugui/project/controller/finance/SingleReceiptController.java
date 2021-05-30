package com.bishugui.project.controller.finance;


import com.bishugui.project.pojo.finance.SinglePayment;
import com.bishugui.project.pojo.finance.SingleReceipt;
import com.bishugui.project.service.finance.SinglePaymentService;
import com.bishugui.project.service.finance.SingleReceiptService;
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
@RequestMapping("/single-receipt")
public class SingleReceiptController {
    @Autowired
    SingleReceiptService service;

    @GetMapping("all")
    public Result getAll(@RequestParam("id") String id){
        List<SingleReceipt> all = service.getAll(id);
        return ResultUtils.success(all);
    }

    @PostMapping("insert")
    public Result insert(@RequestBody SingleReceipt singleReceipt){
        SingleReceipt insert = service.insert(singleReceipt);
        return ResultUtils.success(insert);
    }
}

