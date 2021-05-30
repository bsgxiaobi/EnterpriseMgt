package com.bishugui.project.controller.finance;


import com.bishugui.project.mapper.finance.SingleApMapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.service.customer.CustomerInfoService;
import com.bishugui.project.service.finance.SingleApService;
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
@RequestMapping("/single-ap")
public class SingleApController {
    @Autowired
    SingleApService service;
    @GetMapping("/all")
    public Result getAll(){
        List<SingleAp> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam("id") String id){
        SingleAp one = service.getOne(id);
        return ResultUtils.success(one);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody SingleAp singleAp){
        SingleAp insert = service.insert(singleAp);
        return ResultUtils.success(insert);
    }
}

