package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.Employees;
import com.bishugui.project.pojo.personnel.EmployeesArchives;
import com.bishugui.project.service.personnel.EmployeesArchivesService;
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
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/employees-archives")
public class EmployeesArchivesController {
    @Autowired
    EmployeesArchivesService service;

    @GetMapping("/all")
    public Result getAll(){
        List<EmployeesArchives> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam("employeeId") Integer employeeId){
        EmployeesArchives one = service.getOne(employeeId);
        return ResultUtils.success(one);
    }

    @PostMapping("insert")
    public Result insert(@RequestBody EmployeesArchives archives){
        //System.out.println(archives.toString());
        Boolean insert = service.insert(archives);
        return ResultUtils.success(archives);
    }

    @PostMapping("/update")
    public Result insertOrUpdate(@RequestBody EmployeesArchives archives){
        Boolean update = service.update(archives);
        return ResultUtils.success("success");
    }
}

