package com.bishugui.project.controller.finance;


import com.bishugui.project.pojo.finance.SingleReceipt;
import com.bishugui.project.pojo.finance.Voucher;
import com.bishugui.project.service.finance.VoucherService;
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
@RequestMapping("/voucher")
public class VoucherController {
    @Autowired
    VoucherService service;

    @PostMapping("/insert")
    public Result insert(@RequestBody Voucher voucher){
        Voucher insert = service.insert(voucher);
        return ResultUtils.success(insert);
    }

    @GetMapping("all")
    public Result getAll(){
        List<Voucher> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("all-review")
    public Result getAllReview(){
        List<Voucher> all = service.getAllReview();
        return ResultUtils.success(all);
    }
    @GetMapping("all-bookkeeping")
    public Result getAllBookkeeping(){
        List<Voucher> all = service.getAllBookkeeping();
        return ResultUtils.success(all);
    }

    @PostMapping("/review")
    public Result reviewData(@RequestBody List<Voucher> list){
        String s = service.reviewData(list);
        return ResultUtils.success(s);
    }

    @PostMapping("/bookkeeping")
    public Result bookkeepData(@RequestBody List<Voucher> list){
        String s = service.bookkeepData(list);
        return ResultUtils.success(s);
    }

}

