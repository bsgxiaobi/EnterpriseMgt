package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.service.personnel.DepartmentService;
import com.bishugui.project.service.warehouse.WarehouseService;
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
 * @since 2021-05-22
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    DepartmentService service;


    @GetMapping("/all")
    public Result getAllWarehouseInfo(){
        List<Department> all = service.getAll();
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOneWarehouseInfo(@RequestParam("departmentId") Integer departmentId){
        Department one = service.getOne(departmentId);
        return ResultUtils.success(one);
    }

    @PostMapping("insert-update")
    public Result insertOrUpdateWarehouse(@RequestBody  Department department){
        Department department1 = service.insertOrUpdateOne(department);
        return ResultUtils.success(department1);
    }
}

