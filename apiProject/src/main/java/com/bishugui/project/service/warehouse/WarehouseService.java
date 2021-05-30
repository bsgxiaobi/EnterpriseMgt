package com.bishugui.project.service.warehouse;

import com.bishugui.project.pojo.warehouse.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
public interface WarehouseService extends IService<Warehouse> {
    List<Warehouse> getAllWarehouseInfo();

    Warehouse getOneWarehouseInfo(Integer warehouseId);

    Warehouse insertOrUpdateOneWarehouse(Warehouse warehouse);
}
