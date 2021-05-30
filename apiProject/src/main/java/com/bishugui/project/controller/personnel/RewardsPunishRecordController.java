package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;
import com.bishugui.project.service.personnel.OvertimeRecordService;
import com.bishugui.project.service.personnel.RewardsPunishRecordService;
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
@RequestMapping("/rewards-punish-record")
public class RewardsPunishRecordController {
    @Autowired
    RewardsPunishRecordService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "employeeId",required = false) Integer employeeId){
        List<RewardsPunishRecord> all = service.getAll(employeeId);
        return ResultUtils.success(all);
    }

    @GetMapping("/one")
    public Result getOne(@RequestParam(name = "id") Integer id){
        RewardsPunishRecord record = service.getOneRecord(id);
        return ResultUtils.success(record);
    }

    @PostMapping("insert")
    public Result insertRecord(@RequestBody RewardsPunishRecord record){
        Integer id= service.insertRecord(record);
        return ResultUtils.success(id);
    }

    @PostMapping("revoke")
    public Result changeRecord(@RequestBody RewardsPunishRecord record){
        String time = service.revokeRecord(record);
        return ResultUtils.success(time);
    }
}

