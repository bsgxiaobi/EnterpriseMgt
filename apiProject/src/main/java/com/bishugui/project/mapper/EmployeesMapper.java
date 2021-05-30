package com.bishugui.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bishugui.project.pojo.Employees;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesMapper extends BaseMapper<Employees> {
    Employees getOne(Integer id);
}
