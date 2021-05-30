package com.bishugui.project.controller.warehouse;


import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.bishugui.project.service.warehouse.WarehouseShelvesService;
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
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/warehouse-shelves")
public class WarehouseShelvesController {
    @Autowired
    WarehouseShelvesService warehouseShelvesService;

    @GetMapping("/all")
    public Result getAllWarehouseShelves(@RequestParam("warehouseId") Integer warehouseId){
        List<WarehouseShelves> shelvesList = warehouseShelvesService.getAllWarehouseShelves(warehouseId);
        return ResultUtils.success(shelvesList);
    }


    @PostMapping("insert-update")
    public Result insertOrUpdateShelves(@RequestBody WarehouseShelves shelves){
        WarehouseShelves shelves1 = warehouseShelvesService.insertOrUpdateShelves(shelves);
        return ResultUtils.success(shelves1);
    }

    @PostMapping("delete")
    public Result insertOrUpdateShelves(@RequestBody Map<String,Integer> map){
        String str = warehouseShelvesService.deleteWarehouseShelves(map.get("shelvesId"));
        //if(bl) return ResultUtils.success("删除成功");
       // else return  ResultUtils.fail("删除失败");
        return ResultUtils.success(map);
    }


}

