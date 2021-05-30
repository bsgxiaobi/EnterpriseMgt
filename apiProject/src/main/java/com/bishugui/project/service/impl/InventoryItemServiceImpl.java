package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.warehouse.InventoryItem;
import com.bishugui.project.mapper.warehouse.InventoryItemMapper;
import com.bishugui.project.service.warehouse.InventoryItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Service
public class InventoryItemServiceImpl extends ServiceImpl<InventoryItemMapper, InventoryItem> implements InventoryItemService {
    @Autowired
    InventoryItemMapper inventoryItemMapper;

    @Override
    public List<InventoryItem> getAllParams(Map<String, Object> map) {
        System.out.println(map.toString());
        Integer warehouseId = Integer.parseInt(map.get("warehouseId").toString());
        String title = map.get("title").toString();
        QueryWrapper<InventoryItem> queryWrapper = new QueryWrapper<>();
        if(warehouseId != null && warehouseId > 0){
            queryWrapper.eq("warehouse_id",warehouseId);
        }
        if(title != null && !title.equals("")){
            queryWrapper.like("title",title);
        }
        return inventoryItemMapper.selectList(queryWrapper);
    }

    @Override
    public List<InventoryItem> getAllInventoryItemByShelves(Integer shelvesId) {

        List<InventoryItem> inventoryItems = inventoryItemMapper.getAllInventoryItemByShelves(shelvesId);
        return inventoryItems;
    }

    @Override
    public InventoryItem getOneInventoryItem(Integer iiId) {
        InventoryItem item = inventoryItemMapper.selectById(iiId);
        return item;
    }

    @Override
    public InventoryItem insertOrUpdateInventoryItem(InventoryItem inventoryItem) {
        if(inventoryItem.getIiId() != null && inventoryItem.getIiId() > 0){
            inventoryItemMapper.updateById(inventoryItem);
        }else{
            int i = inventoryItemMapper.insert(inventoryItem);
        }
        return inventoryItem;
    }
}
