package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bishugui.project.mapper.sales.SalesItemMapper;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.pojo.sales.SalesQuotation;
import com.bishugui.project.mapper.sales.SalesQuotationMapper;
import com.bishugui.project.service.sales.SalesQuotationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-09
 */
@Service
public class SalesQuotationServiceImpl extends ServiceImpl<SalesQuotationMapper, SalesQuotation> implements SalesQuotationService {
    @Autowired
    SalesQuotationMapper salesQuotationMapper;
    @Autowired
    SalesItemMapper salesItemMapper;

    @Override
    public List<SalesQuotation> getAllSalesQuotation(Integer employeeId,String state) {
        List<SalesQuotation> salesQuotationList = salesQuotationMapper.getAllSalesQuotation(employeeId,state);
        return salesQuotationList;
    }

    @Override
    public SalesQuotation getOneSalesQuotation(String sqId) {
        SalesQuotation salesQuotation = salesQuotationMapper.selectById(sqId);
        return salesQuotation;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public SalesQuotation insertOrUpdateSalesQuotation(SalesQuotation salesQuotation) {
        //设置经办日期
        salesQuotation.setAgentDate(LocalDateTime.now());
        //如果询价单的主键存在则转为更新
        if(salesQuotation.getSqId().equals("")){
            //插入询价单
            //生成主键
            String sqId ="xx" + BasicUtils.generateBillPrimaryKey();
            salesQuotation.setSqId(sqId);
            int insert = salesQuotationMapper.insert(salesQuotation);
            //插入销售报价单
            for (SalesItem item : salesQuotation.getSalesItemList()) {
                item.setSqId(sqId);
                salesItemMapper.insert(item);
            }
        }else{
            //更新报价单
            int i = salesQuotationMapper.updateById(salesQuotation);
            for (SalesItem item : salesQuotation.getSalesItemList()) {
                salesItemMapper.updateById(item);
            }
        }
        salesQuotation.setSalesItemList(null);
        return salesQuotation;
    }

    @Override
    public Boolean updateState(Map<String,String> map) {
        UpdateWrapper<SalesQuotation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sq_id",map.get("sqId"));
        updateWrapper.set("state",map.get("state"));
//        if(salesQuotation.getDeliveryAgent() != null){
//            updateWrapper.set("delivery_agent",salesQuotation.getDeliveryAgent());
//            updateWrapper.set("delivery_agent_date",BasicUtils.getLocalDateTimeToString());
//            updateWrapper.set("delivery_note",salesQuotation.getDeliveryNote());
//        }
        salesQuotationMapper.update(null,updateWrapper);
        return true;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public SalesQuotation deliveryGoods(SalesQuotation salesQuotation) {
        //更新销售报价单
        UpdateWrapper<SalesQuotation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sq_id",salesQuotation.getSqId());
        updateWrapper.set("delivery_agent",salesQuotation.getDeliveryAgent());
        salesQuotation.setDeliveryAgentDate(LocalDateTime.now());
        updateWrapper.set("delivery_agent_date",BasicUtils.getLocalDateTimeToString());
        updateWrapper.set("delivery_total",salesQuotation.getDeliveryTotal());
        updateWrapper.set("delivery_note",salesQuotation.getDeliveryNote());
        updateWrapper.set("warehouse",salesQuotation.getWarehouse());
        updateWrapper.set("state",salesQuotation.getState());
        int i = salesQuotationMapper.update(null, updateWrapper);

        //更新销售清单
        Integer integer = salesItemMapper.updateForDelivery(salesQuotation.getSalesItemList());

        return salesQuotation;
    }

    @Override
    public List<SalesQuotation> getAllDeliveryQuotation(Integer agent) {
        List<SalesQuotation> salesQuotationList = salesQuotationMapper.getAllDeliveryQuotation(agent);
        return salesQuotationList;
    }
}
