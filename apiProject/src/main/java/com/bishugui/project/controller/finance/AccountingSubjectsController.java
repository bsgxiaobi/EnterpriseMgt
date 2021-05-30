package com.bishugui.project.controller.finance;


import com.bishugui.project.mapper.finance.AccountingSubjectsMapper;
import com.bishugui.project.pojo.finance.AccountingSubjects;
import com.bishugui.project.service.finance.AccountingSubjectsService;
import com.bishugui.project.utils.Result;
import com.bishugui.project.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/accounting-subjects")
public class AccountingSubjectsController {
    @Autowired
    AccountingSubjectsService service;

    @GetMapping("/all")
    public Result getAll(){
        List<AccountingSubjects> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/all-general")
    public Result getGeneralClassLedger(@RequestParam Map<String,String> map){
        List<AccountingSubjects> generalClassLedger = service.getGeneralClassLedger(map);
        return ResultUtils.success(generalClassLedger);
    }

}

