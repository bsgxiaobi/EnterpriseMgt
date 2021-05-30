package com.bishugui.project.mapper.customer;

import com.bishugui.project.pojo.customer.CustomerVisit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bishugui.project.service.customer.CustomerVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-10
 */
@Repository
public interface CustomerVisitMapper extends BaseMapper<CustomerVisit> {
    List<CustomerVisit> getAll(Integer cvId);

    List<CustomerVisit> getSelectResult(Map<String, Object> map);
}
