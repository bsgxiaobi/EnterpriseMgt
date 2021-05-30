package com.bishugui.project.controller.finance;


import com.bishugui.project.pojo.finance.SingleAp;
import com.bishugui.project.pojo.finance.SingleAr;
import com.bishugui.project.service.finance.SingleApService;
import com.bishugui.project.service.finance.SingleArService;
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
@RequestMapping("/single-ar")
public class SingleArController {
    @Autowired
    SingleArService service;
    @GetMapping("/all")
    public Result getAll(){
        List<SingleAr> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam("id") String id){
        SingleAr one = service.getOne(id);
        return ResultUtils.success(one);
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody SingleAr singleAr){
        SingleAr insert = service.insert(singleAr);
        return ResultUtils.success(insert);
    }
}

