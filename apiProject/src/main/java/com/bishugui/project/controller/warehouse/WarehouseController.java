package com.bishugui.project.controller.warehouse;


import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.service.warehouse.WarehouseService;
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
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/all")
    public Result getAllWarehouseInfo(){
        List<Warehouse> allWarehouseInfo = warehouseService.getAllWarehouseInfo();
        return ResultUtils.success(allWarehouseInfo);
    }

    @GetMapping("/one")
    public Result getOneWarehouseInfo(@RequestParam("warehouseId") Integer warehouseId){
        Warehouse info = warehouseService.getOneWarehouseInfo(warehouseId);
        return ResultUtils.success(info);
    }

    @PostMapping("insert-update")
    public Result insertOrUpdateWarehouse(@RequestBody  Warehouse warehouse){
        Warehouse warehouse1 = warehouseService.insertOrUpdateOneWarehouse(warehouse);
        return ResultUtils.success(warehouse1);
    }
}

