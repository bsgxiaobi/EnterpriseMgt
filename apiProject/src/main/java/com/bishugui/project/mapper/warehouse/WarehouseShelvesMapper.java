package com.bishugui.project.mapper.warehouse;

import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Repository
public interface WarehouseShelvesMapper extends BaseMapper<WarehouseShelves> {
    //获取某个仓库下的所有结构
    List<WarehouseShelves> getAllShelvesByWarehouse(Integer warehouseId);

    //获取所有子货架
    List<WarehouseShelves> getAllSonShelvesById(Integer shelvesId);
}
