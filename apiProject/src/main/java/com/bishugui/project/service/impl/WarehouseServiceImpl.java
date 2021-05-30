package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.mapper.warehouse.WarehouseMapper;
import com.bishugui.project.service.warehouse.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {
    @Autowired
    WarehouseMapper warehouseMapper;
    @Override
    public List<Warehouse> getAllWarehouseInfo() {

        return warehouseMapper.selectList(null);
    }

    @Override
    public Warehouse getOneWarehouseInfo(Integer warehouseId) {
        QueryWrapper<Warehouse> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("warehouse_id",warehouseId);
        Warehouse warehouse = warehouseMapper.selectById(warehouseId);
        return warehouse;
    }

    @Override
    public Warehouse insertOrUpdateOneWarehouse(Warehouse warehouse) {
        warehouse.setUpdateDate(LocalDateTime.now());
        if(warehouse.getWarehouseId() != null && warehouse.getWarehouseId() > 0){
            warehouseMapper.updateById(warehouse);
        }else{
            int i = warehouseMapper.insert(warehouse);
        }
        return warehouse;
    }
}
