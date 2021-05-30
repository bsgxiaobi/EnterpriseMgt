package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.mapper.personnel.DepartmentMapper;
import com.bishugui.project.pojo.warehouse.Warehouse;
import com.bishugui.project.service.personnel.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-22
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {
    @Autowired
    DepartmentMapper mapper;

    @Override
    public List<Department> getSelectResult(Map<String, Object> map) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id",map.get("departmentId"));
        if(map.get("startDate") != null && map.get("startDate") != ""){
            queryWrapper.between("apply_date",map.get("startDate"),map.get("endDate"));
        }
        return mapper.selectList(queryWrapper);
    }

    @Override
    public List<Department> getAll() {
        return mapper.selectList(null);
    }

    @Override
    public Department getOne(Integer departmentId) {
        return mapper.selectById(departmentId);
    }

    @Override
    public Department insertOrUpdateOne(Department department) {
        department.setUpdateDate(LocalDateTime.now());
        if(department.getDepartmentId() != null && department.getDepartmentId() > 0){
            mapper.updateById(department);
        }else{
            int i = mapper.insert(department);
        }
        return department;
    }
}
