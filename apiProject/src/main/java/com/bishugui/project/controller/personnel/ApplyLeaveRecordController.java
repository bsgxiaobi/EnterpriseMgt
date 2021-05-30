package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.bishugui.project.service.personnel.ApplyLeaveRecordService;
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
@RequestMapping("/apply-leave-record")
public class ApplyLeaveRecordController {
    @Autowired
    ApplyLeaveRecordService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "employeeId",required = false) Integer employeeId){
        List<ApplyLeaveRecord> all = service.getAll(employeeId);
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam(name = "alrId") Integer alrId){
        ApplyLeaveRecord applyLeaveRecord = service.getOneRecord(alrId);
        return ResultUtils.success(applyLeaveRecord);
    }

    @PostMapping("insert")
    public Result insertRecord(@RequestBody ApplyLeaveRecord applyLeaveRecord){
        Integer id = service.insertRecord(applyLeaveRecord);
        return ResultUtils.success(id);
    }

    @PostMapping("examine")
    public Result changeRecord(@RequestBody ApplyLeaveRecord applyLeaveRecord){
        String time = service.examineRecord(applyLeaveRecord);
        return ResultUtils.success(time);
    }

}

