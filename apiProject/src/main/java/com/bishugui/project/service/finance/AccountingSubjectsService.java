package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.finance.AccountingSubjects;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
public interface AccountingSubjectsService extends IService<AccountingSubjects> {
    List<AccountingSubjects> getAll();

    List<AccountingSubjects> getGeneralClassLedger(Map<String,String> map);
}
