package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bishugui.project.mapper.warehouse.ClaimItemMapper;
import com.bishugui.project.pojo.sales.SalesAfter;
import com.bishugui.project.pojo.sales.SalesItem;
import com.bishugui.project.pojo.warehouse.ClaimItem;
import com.bishugui.project.pojo.warehouse.InventoryClaim;
import com.bishugui.project.mapper.warehouse.InventoryClaimMapper;
import com.bishugui.project.service.warehouse.InventoryClaimService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
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
 * @since 2021-05-18
 */
@Service
public class InventoryClaimServiceImpl extends ServiceImpl<InventoryClaimMapper, InventoryClaim> implements InventoryClaimService {
    @Autowired
    InventoryClaimMapper inventoryClaimMapper;
    @Autowired
    ClaimItemMapper claimItemMapper;

    @Override
    public List<InventoryClaim> getAllInventoryClaim(Integer applyId) {
        List<InventoryClaim> inventoryClaimList;
        if(applyId != null){
            QueryWrapper<InventoryClaim> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_id",applyId);
            inventoryClaimList = inventoryClaimMapper.selectList(queryWrapper);
        }else{
            inventoryClaimList = inventoryClaimMapper.selectList(null);
        }
        return inventoryClaimList;
    }

    @Override
    public InventoryClaim addOneInventoryClaim(InventoryClaim inventoryClaim) {
        return null;
    }

    @Override
    public InventoryClaim getOneInventoryClaim(String claimId) {
        QueryWrapper<InventoryClaim> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("claim_id",claimId);
        InventoryClaim inventoryClaim = inventoryClaimMapper.selectOne(queryWrapper);
        return inventoryClaim;
    }

    @Override
    public InventoryClaim insertOrUpdateInventoryClaim(InventoryClaim inventoryClaim) {
        //判断是申请还是发放
        if(inventoryClaim.getApplyId() != null){
            inventoryClaim.setApplyDate(LocalDateTime.now());
        }else{
            inventoryClaim.setAgentDate(LocalDateTime.now());
        }

        //如果询价单的主键存在则转为更新
        if(inventoryClaim.getClaimId() == null || inventoryClaim.getClaimId().equals("")){
            //插入询价单
            //生成主键
            String claimId ="zf" + BasicUtils.generateBillPrimaryKey();
            inventoryClaim.setClaimId(claimId);
            int insert = inventoryClaimMapper.insert(inventoryClaim);
            //插入支领发放单的清单
            for (ClaimItem item : inventoryClaim.getClaimItemList()) {
                item.setClaimId(claimId);
                claimItemMapper.insert(item);
            }
        }else{
            //更新支领发放单
            int i = inventoryClaimMapper.updateById(inventoryClaim);
            for (ClaimItem item : inventoryClaim.getClaimItemList()) {
                claimItemMapper.updateById(item);
            }
        }
        inventoryClaim.setClaimItemList(null);
        return inventoryClaim;
    }

    @Override
    public String approvalInventoryClaim(InventoryClaim inventoryClaim) {
        UpdateWrapper<InventoryClaim> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("apply_state",inventoryClaim.getApplyState());
        updateWrapper.set("agent_id",inventoryClaim.getAgentId());
        String currentTime = BasicUtils.getLocalDateTimeToString();
        updateWrapper.set("agent_date",currentTime);
        updateWrapper.eq("claim_id",inventoryClaim.getClaimId());
        int update = inventoryClaimMapper.update(null, updateWrapper);
        return currentTime;
    }
}
