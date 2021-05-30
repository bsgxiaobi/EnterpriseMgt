package com.bishugui.project.service.warehouse;

import com.bishugui.project.pojo.warehouse.ClaimItem;
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
public interface ClaimItemService extends IService<ClaimItem> {
    //根据支领单id获取所有的
    List<ClaimItem> getAllByClaimId(String claimId);



}
