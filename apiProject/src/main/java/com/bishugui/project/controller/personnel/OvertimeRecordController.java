package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.BusinessTripRecord;
import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.service.personnel.BusinessTripRecordService;
import com.bishugui.project.service.personnel.OvertimeRecordService;
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
 * @since 2021-05-22
 */
@RestController
@RequestMapping("/overtime-record")
public class OvertimeRecordController {
    @Autowired
    OvertimeRecordService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "employeeId",required = false) Integer employeeId){
        List<OvertimeRecord> all = service.getAll(employeeId);
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam(name = "orId") Integer orId){
        OvertimeRecord overtimeRecord = service.getOneRecord(orId);
        return ResultUtils.success(overtimeRecord);
    }

    @PostMapping("insert")
    public Result insertRecord(@RequestBody OvertimeRecord overtimeRecord){
        Integer id = service.insertRecord(overtimeRecord);
        return ResultUtils.success(id);
    }

    @PostMapping("examine")
    public Result changeRecord(@RequestBody OvertimeRecord overtimeRecord){
        String time = service.examineRecord(overtimeRecord);
        return ResultUtils.success(time);
    }
}

