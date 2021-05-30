package com.bishugui.project.mapper.finance;

import com.bishugui.project.pojo.finance.AccountingSubjects;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
@Repository
public interface AccountingSubjectsMapper extends BaseMapper<AccountingSubjects> {
    @Select("select * from accounting_subjects")
    List<AccountingSubjects> getAll();

    List<AccountingSubjects> getGeneralClassLedger(Map<String,String> map);
}
