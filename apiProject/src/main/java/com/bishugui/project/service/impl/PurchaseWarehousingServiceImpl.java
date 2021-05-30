package com.bishugui.project.service.impl;

import com.bishugui.project.mapper.PurchaseInquiryMapper;
import com.bishugui.project.mapper.PurchaseItemMapper;
import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.pojo.PurchaseInquiry;
import com.bishugui.project.pojo.PurchaseItem;
import com.bishugui.project.pojo.PurchaseWarehousing;
import com.bishugui.project.mapper.PurchaseWarehousingMapper;
import com.bishugui.project.service.PurchaseWarehousingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-05
 */
@Service
public class PurchaseWarehousingServiceImpl extends ServiceImpl<PurchaseWarehousingMapper, PurchaseWarehousing> implements PurchaseWarehousingService {

    @Autowired
    PurchaseItemMapper purchaseItemMapper;
    @Autowired
    PurchaseWarehousingMapper purchaseWarehousingMapper;
    @Autowired
    PurchaseInquiryMapper purchaseInquiryMapper;
    @Override
    public List<PurchaseWarehousing> getAllPurchaseWarehousing(Integer agentId) {
        List<PurchaseWarehousing> purchaseWarehousingList = new ArrayList<>();

        //先查出已经入库了的入库单
        List<PurchaseWarehousing> allPurchaseWarehousing = purchaseWarehousingMapper.getAllPurchaseWarehousing(agentId);
        allPurchaseWarehousing.forEach(System.out::println);
        //将查询得到的询价单单号提取
        List<String> piIdList = null;
        if(allPurchaseWarehousing != null && allPurchaseWarehousing.size() > 0){
            piIdList = new ArrayList<>();
            for (PurchaseWarehousing purchaseWarehousing : allPurchaseWarehousing) {
                piIdList.add(purchaseWarehousing.getPiId());
            }
        }

        //查询还没入库的询价单
        List<PurchaseInquiry> inquiryList = purchaseInquiryMapper.getAllPurchaseInquiryExcludePoId(piIdList);
        for (PurchaseInquiry purchaseInquiry : inquiryList) {
            purchaseWarehousingList.add(new PurchaseWarehousing(purchaseInquiry));
        }

        //合并两个list
        purchaseWarehousingList.addAll(allPurchaseWarehousing);

        return purchaseWarehousingList;
    }

    @Override
    @Transactional(propagation= Propagation.NESTED,isolation= Isolation.DEFAULT,readOnly = false,rollbackFor=Exception.class)
    public PurchaseWarehousing insertInquiryAndUpdateItem(PurchaseWarehousing purchaseWarehousing) {
        //设置经办日期
        purchaseWarehousing.setAgentDate(LocalDateTime.now());
        //如果询价单的主键存在则转为更新
        if(purchaseWarehousing.getPwId().equals("")){
            //插入询价单
            //生成主键
            String piId ="cr" + BasicUtils.generateBillPrimaryKey();
            purchaseWarehousing.setPwId(piId);
            int insert = purchaseWarehousingMapper.insert(purchaseWarehousing);
        }else{
            //更新询价单
            int i = purchaseWarehousingMapper.updateById(purchaseWarehousing);
        }

        //更新采购清单
        List<PurchaseItem> itemList = purchaseWarehousing.getPurchaseItemList();
        Integer i = purchaseItemMapper.updatePurchaseItemByIdForWareHousing(itemList);

        purchaseWarehousing.setPurchaseItemList(null);
        return purchaseWarehousing;
    }


}
