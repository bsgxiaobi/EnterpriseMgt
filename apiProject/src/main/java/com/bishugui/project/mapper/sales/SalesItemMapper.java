package com.bishugui.project.mapper.sales;

import com.bishugui.project.pojo.sales.SalesItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
@Repository
public interface SalesItemMapper extends BaseMapper<SalesItem> {
    //发货时批量更新
    Integer updateForDelivery(List<SalesItem> list);
}
