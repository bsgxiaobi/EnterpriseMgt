package com.bishugui.project.service.warehouse;

import com.bishugui.project.pojo.warehouse.WarehouseShelves;
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
public interface WarehouseShelvesService extends IService<WarehouseShelves> {
    List<WarehouseShelves> getAllWarehouseShelves(Integer warehouseId);

    WarehouseShelves insertOrUpdateShelves(WarehouseShelves warehouseShelves);

    String deleteWarehouseShelves(Integer shelvesId);
}
