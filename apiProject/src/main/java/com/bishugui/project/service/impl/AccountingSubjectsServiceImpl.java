package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.finance.AccountingSubjects;
import com.bishugui.project.mapper.finance.AccountingSubjectsMapper;
import com.bishugui.project.service.finance.AccountingSubjectsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
@Service
public class AccountingSubjectsServiceImpl extends ServiceImpl<AccountingSubjectsMapper, AccountingSubjects> implements AccountingSubjectsService {
    @Autowired
    AccountingSubjectsMapper mapper;

    @Override
    public List<AccountingSubjects> getAll() {
        return mapper.getAll();
    }

    @Override
    public List<AccountingSubjects> getGeneralClassLedger(Map<String,String> map) {
        if(map.get("startDate") == ""){
            map.put("startDate",null);
            map.put("endDate",null);
        }
        return mapper.getGeneralClassLedger(map);
    }
}
