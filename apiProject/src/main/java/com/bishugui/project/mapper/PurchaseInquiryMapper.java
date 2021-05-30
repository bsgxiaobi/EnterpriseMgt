package com.bishugui.project.mapper;

import com.bishugui.project.pojo.PurchaseInquiry;
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
public interface PurchaseInquiryMapper extends BaseMapper<PurchaseInquiry> {
    //获取所有采购询价单
    List<PurchaseInquiry> getAllPurchaseInquiry(Integer agentId);

    //更新状态
    Integer updateInquiryState(Map<String,String> map);

    //获取还没入库的询价单
    List<PurchaseInquiry> getAllPurchaseInquiryExcludePoId(List<String> list);
}
