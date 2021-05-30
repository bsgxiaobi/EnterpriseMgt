package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.mapper.EmployeesMapper;
import com.bishugui.project.pojo.Employees;
import com.bishugui.project.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeesServiceImpl extends ServiceImpl<EmployeesMapper, Employees> implements EmployeesService {
    @Autowired
    EmployeesMapper mapper;

    @Override
    public Employees getEmployeeById(Integer employeeId) {
        Employees one = mapper.getOne(employeeId);
        return one;
    }

    @Override
    public List<Employees> getAll() {
        return mapper.selectList(null);
    }

    @Override
    public Employees insertOrUpdate(Employees employees) {
        if(employees.getEmployeeId() != null && employees.getEmployeeId() > 0){
            int i = mapper.updateById(employees);
        }else{
            System.out.println(employees.toString());
            int insert = mapper.insert(employees);
        }
        return employees;
    }

    @Override
    public String updatePwd(Map<String, Object> map) {
        //验证现密码是否正确
        QueryWrapper<Employees> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("employee_id",map.get("employeeId"));
        queryWrapper.eq("pwd",map.get("pwd"));
        Integer count = mapper.selectCount(queryWrapper);
        if(count == 0){
            return "原密码错误";
        }
        UpdateWrapper<Employees> updateWrapper = new UpdateWrapper<>();
        Employees employees = new Employees();
        employees.setEmployeeId((int)map.get("employeeId"));
        employees.setPwd(map.get("newPwd").toString());
        int i = mapper.updateById(employees);
        if(i>0) return "success";
        else return "更新密码失败";
    }

    @Override
    public Map<String,Object> login(Map<String, Object> map) {
        Map<String,Object> result = new HashMap<>();
        Employees employees = mapper.selectById(Integer.parseInt(map.get("employeeId").toString()));
        if(employees == null){
            result.put("msg","用户不存在");
            result.put("employee",null);
            return result;
        }
        //获取身份证后6位
        String str = employees.getCardId().substring(12);
        System.out.println(str);
        System.out.println(map.get("pwd").toString());
        //判断已存在的密码是是否与身份证后6位相等
        if(!map.get("pwd").toString().equals(employees.getPwd())){
            System.out.println("error");
            result.put("msg","error");
            result.put("employee",null);
            return result;
        }
        Employees one = mapper.getOne(employees.getEmployeeId());
        if(employees.getPwd().equals(str)){
            System.out.println("warning");
            result.put("msg","warning");
            result.put("employee",one);
            return result;
        }else {
            result.put("msg","success");
            result.put("employee",one);
            return result;
        }
    }
}
