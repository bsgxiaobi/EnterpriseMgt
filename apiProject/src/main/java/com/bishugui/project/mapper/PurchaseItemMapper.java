package com.bishugui.project.mapper;

import com.bishugui.project.pojo.PurchaseItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Repository
public interface PurchaseItemMapper extends BaseMapper<PurchaseItem> {

    Integer updatePurchaseItemById(List<PurchaseItem> list);

    Integer updatePurchaseItemByIdForWareHousing(List<PurchaseItem> list);
}
