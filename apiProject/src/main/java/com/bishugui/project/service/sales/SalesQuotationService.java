package com.bishugui.project.service.sales;

import com.bishugui.project.pojo.sales.SalesQuotation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
public interface SalesQuotationService extends IService<SalesQuotation> {

    //拉取所有的销售询价单,可以选顶指定员工操作的询价单
    List<SalesQuotation> getAllSalesQuotation(Integer employeeId,String state);

    //根据询价单编号，获取单条询价单信息
    SalesQuotation getOneSalesQuotation(String sqId);

    //添加或者更新一条销售询价单,并将数据返回到前台
    SalesQuotation insertOrUpdateSalesQuotation(SalesQuotation salesQuotation);

    //更新状态
    Boolean updateState(Map<String,String> map);

    //发货
    SalesQuotation deliveryGoods(SalesQuotation salesQuotation);

    //获取销售出货单界面的数据
    List<SalesQuotation> getAllDeliveryQuotation(Integer agent);
}
