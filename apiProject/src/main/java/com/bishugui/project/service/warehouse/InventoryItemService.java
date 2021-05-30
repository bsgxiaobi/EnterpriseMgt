package com.bishugui.project.service.warehouse;

import com.bishugui.project.pojo.warehouse.InventoryItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
public interface InventoryItemService extends IService<InventoryItem> {
    List<InventoryItem> getAllParams(Map<String,Object> map);

    List<InventoryItem> getAllInventoryItemByShelves(Integer shelvesId);

    InventoryItem getOneInventoryItem(Integer iiId);

    InventoryItem insertOrUpdateInventoryItem(InventoryItem inventoryItem);
}
