package com.bishugui.project.controller.warehouse;


import com.bishugui.project.pojo.warehouse.InventoryItem;
import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.bishugui.project.service.warehouse.InventoryItemService;
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
@RequestMapping("/inventory-item")
public class InventoryItemController {
    @Autowired
    private InventoryItemService service;

    @GetMapping("/one")
    public Result getOneInventoryItem(@RequestParam("iiId") Integer iiId){
        InventoryItem inventoryItem = service.getOneInventoryItem(iiId);
        return ResultUtils.success(inventoryItem);
    }
    @GetMapping("all-params")
    public Result getAllParams(@RequestParam Map<String,Object> map){
        List<InventoryItem> itemList = service.getAllParams(map);
        return ResultUtils.success(itemList);
    }
    @GetMapping("/all")
    public Result getAllInventoryItem(@RequestParam("shelvesId") Integer shelvesId){
        List<InventoryItem> itemList = service.getAllInventoryItemByShelves(shelvesId);
        return ResultUtils.success(itemList);
    }

    @PostMapping("insert-update")
    public Result insertOrUpdateWarehouse(@RequestBody InventoryItem inventoryItem){
        InventoryItem item = service.insertOrUpdateInventoryItem(inventoryItem);
        return ResultUtils.success(item);
    }
}

