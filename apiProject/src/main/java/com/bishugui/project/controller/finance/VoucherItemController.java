package com.bishugui.project.controller.finance;


import com.bishugui.project.pojo.finance.Voucher;
import com.bishugui.project.pojo.finance.VoucherItem;
import com.bishugui.project.service.finance.VoucherItemService;
import com.bishugui.project.service.finance.VoucherService;
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
 * @since 2021-05-24
 */
@RestController
@RequestMapping("/voucher-item")
public class VoucherItemController {
    @Autowired
    VoucherItemService service;

    @GetMapping("all")
    public Result getAll(@RequestParam("id") Integer id){
        List<VoucherItem> all = service.getAll(id);
        return ResultUtils.success(all);
    }

    @GetMapping("all-params")
    public Result getAllByParams(@RequestParam Map<String,Object> map){
        List<VoucherItem> all = service.getAllByParams(map);
        return ResultUtils.success(all);
    }
}

