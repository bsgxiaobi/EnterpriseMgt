package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.pojo.personnel.Recruit;
import com.bishugui.project.service.personnel.RecruitService;
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
 * @since 2021-05-23
 */
@RestController
@RequestMapping("/recruit")
public class RecruitController {
    @Autowired
    RecruitService service;

    @GetMapping("/all-params")
    public Result getSelectResult(@RequestParam Map<String,Object> map){
        List<Recruit> list = service.getSelectResult(map);
        return ResultUtils.success(list);
    }
    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "departmentId",required = false) Integer departmentId){
        List<Recruit> all = service.getAll(departmentId);
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam(name = "id") Integer id){
        Recruit record = service.getOneRecord(id);
        return ResultUtils.success(record);
    }

    @PostMapping("insert")
    public Result insertRecord(@RequestBody Recruit record){
        Integer id= service.insertRecord(record);
        return ResultUtils.success(id);
    }

    @PostMapping("implement")
    public Result changeRecord(@RequestBody Recruit record){
        String time = service.updateRecord(record);
        return ResultUtils.success(time);
    }
}

