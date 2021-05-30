package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.pojo.sales.SalesAfter;
import com.bishugui.project.mapper.sales.SalesAfterMapper;
import com.bishugui.project.service.sales.SalesAfterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
@Service
public class SalesAfterServiceImpl extends ServiceImpl<SalesAfterMapper, SalesAfter> implements SalesAfterService {

    @Autowired
    SalesAfterMapper salesAfterMapper;

    @Override
    public List<SalesAfter> getAllAfter(Integer agent) {
        List<SalesAfter> afterList;
        if(agent != null){
            QueryWrapper<SalesAfter> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("agent",agent);
            afterList = salesAfterMapper.selectList(queryWrapper);
        }else{
            afterList = salesAfterMapper.selectList(null);
        }
        return afterList;
    }

    @Override
    public SalesAfter getOneAfter(String saId) {
        QueryWrapper<SalesAfter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sa_id",saId);
        SalesAfter salesAfter = salesAfterMapper.selectOne(queryWrapper);
        return salesAfter;
    }

    @Override
    public SalesAfter insertOrUpdateAfter(SalesAfter salesAfter) {
        //设置经办日期
        salesAfter.setAgentDate(LocalDateTime.now());
        //如果询价单的主键存在则转为更新
        if(salesAfter.getSaId().equals("")){
            //插入询价单
            salesAfter.setApplyDate(LocalDateTime.now());
            //生成主键
            String saId ="xs" + BasicUtils.generateBillPrimaryKey();
            salesAfter.setSaId(saId);
            int insert = salesAfterMapper.insert(salesAfter);
        }else{
            //更新询价单
            int i = salesAfterMapper.updateById(salesAfter);
        }

        return salesAfter;
    }
}
