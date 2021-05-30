package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.bishugui.project.pojo.personnel.BusinessTripRecord;
import com.bishugui.project.service.personnel.ApplyLeaveRecordService;
import com.bishugui.project.service.personnel.BusinessTripRecordService;
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
@RequestMapping("/business-trip-record")
public class BusinessTripRecordController {
    @Autowired
    BusinessTripRecordService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "employeeId",required = false) Integer employeeId){
        List<BusinessTripRecord> all = service.getAll(employeeId);
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam(name = "btId") Integer btId){
        BusinessTripRecord businessTripRecord = service.getOneRecord(btId);
        return ResultUtils.success(businessTripRecord);
    }

    @PostMapping("insert")
    public Result insertRecord(@RequestBody BusinessTripRecord businessTripRecord){
        Integer id = service.insertRecord(businessTripRecord);
        return ResultUtils.success(id);
    }

    @PostMapping("examine")
    public Result changeRecord(@RequestBody BusinessTripRecord businessTripRecord){
        String time = service.examineRecord(businessTripRecord);
        return ResultUtils.success(time);
    }
}

