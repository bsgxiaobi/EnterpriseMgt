package com.bishugui.project.mapper.warehouse;

import com.bishugui.project.pojo.warehouse.InventoryItem;
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
public interface InventoryItemMapper extends BaseMapper<InventoryItem> {
    List<InventoryItem> getAllInventoryItemByShelves(Integer shelvesId);
}
