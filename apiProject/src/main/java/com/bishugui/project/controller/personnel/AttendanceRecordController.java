package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.bishugui.project.service.personnel.AttendanceRecordService;
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
@RequestMapping("/attendance-record")
public class AttendanceRecordController {
    @Autowired
    AttendanceRecordService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "employeeId",required = false) Integer employeeId){
        List<AttendanceRecord> all = service.getAll(employeeId);
        return ResultUtils.success(all);
    }

    @PostMapping("represent")
    public Result representRecord(@RequestBody AttendanceRecord attendanceRecord){
        AttendanceRecord record = service.representRecord(attendanceRecord);
        return ResultUtils.success(record);
    }

    @PostMapping("change")
    public Result changeRecord(@RequestBody AttendanceRecord attendanceRecord){
        AttendanceRecord record = service.changeRecord(attendanceRecord);
        return ResultUtils.success(record);
    }
}

