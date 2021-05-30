package com.bishugui.project.service.sales;

import com.bishugui.project.pojo.sales.SalesItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
public interface SalesItemService extends IService<SalesItem> {
    //根据销售询价单编号查询出清单列表
    List<SalesItem> getAllSalesItemBySqId(String sqId);

}
