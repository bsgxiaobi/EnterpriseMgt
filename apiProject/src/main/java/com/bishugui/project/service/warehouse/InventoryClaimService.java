package com.bishugui.project.service.warehouse;

import com.bishugui.project.pojo.warehouse.InventoryClaim;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
public interface InventoryClaimService extends IService<InventoryClaim> {
    List<InventoryClaim> getAllInventoryClaim(Integer applyId);

    InventoryClaim addOneInventoryClaim(InventoryClaim inventoryClaim);

    InventoryClaim getOneInventoryClaim(String claimId);

    InventoryClaim insertOrUpdateInventoryClaim(InventoryClaim inventoryClaim);

    String approvalInventoryClaim(InventoryClaim inventoryClaim);
}
