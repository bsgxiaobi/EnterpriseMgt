package com.bishugui.project.mapper;

import com.bishugui.project.pojo.PurchaseApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Repository
public interface PurchaseApplyMapper extends BaseMapper<PurchaseApply> {
    List<PurchaseApply> getAllPurchaseApply(Map<String,String> map);

    Integer updateAuditState(Map<String,String> map);

    List<PurchaseApply> getAllPurchaseApplyById(List<String> list);

    List<PurchaseApply> getAllPurchaseApplyExcludePoId(List<String> list);


}
