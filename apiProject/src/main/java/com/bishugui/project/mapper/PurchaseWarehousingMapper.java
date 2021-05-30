package com.bishugui.project.mapper;

import com.bishugui.project.pojo.PurchaseWarehousing;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Repository
public interface PurchaseWarehousingMapper extends BaseMapper<PurchaseWarehousing> {
    List<PurchaseWarehousing> getAllPurchaseWarehousing(Integer agentId);
}
