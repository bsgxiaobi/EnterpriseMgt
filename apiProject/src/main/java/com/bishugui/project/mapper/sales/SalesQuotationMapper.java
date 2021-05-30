package com.bishugui.project.mapper.sales;

import com.bishugui.project.pojo.sales.SalesQuotation;
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
public interface SalesQuotationMapper extends BaseMapper<SalesQuotation> {
    //获取所有销售报价单，员工id为为空
    List<SalesQuotation> getAllSalesQuotation(Integer employeeId,String state);

    //获取销售出货单界面的数据
    List<SalesQuotation> getAllDeliveryQuotation(Integer employeeId);

}
