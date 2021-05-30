package com.bishugui.project.controller.personnel;


import com.bishugui.project.pojo.personnel.Position;
import com.bishugui.project.pojo.personnel.Recruit;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.bishugui.project.service.personnel.PositionService;
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
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService service;

    @GetMapping("/all")
    public Result getAll(@RequestParam(name = "departmentId") Integer departmentId){
        List<Position> all = service.getAll(departmentId);
        return ResultUtils.success(all);
    }
    @GetMapping("/all-position")
    public Result getAllPosition(@RequestParam(name = "departmentId") Integer departmentId){
        List<Position> all = service.getAllPosition(departmentId);
        return ResultUtils.success(all);
    }

    @PostMapping("insert-update")
    public Result insertOrUpdateShelves(@RequestBody Position position){
        Position position1 = service.insertOrUpdate(position);
        return ResultUtils.success(position);
    }
}

