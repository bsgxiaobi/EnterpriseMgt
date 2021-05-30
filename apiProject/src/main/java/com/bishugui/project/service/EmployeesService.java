package com.bishugui.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.mapper.EmployeesMapper;
import com.bishugui.project.pojo.Employees;
import com.bishugui.project.pojo.PurchaseApply;
import com.bishugui.project.service.personnel.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface EmployeesService extends IService<Employees> {
    Employees getEmployeeById(Integer employeeId);

    List<Employees> getAll();

    Employees insertOrUpdate(Employees employees);

    String updatePwd(Map<String,Object> map);

    Map<String,Object> login(Map<String,Object> map);
}
