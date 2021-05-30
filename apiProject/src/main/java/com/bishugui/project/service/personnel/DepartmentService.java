package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.personnel.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bishugui.project.pojo.warehouse.Warehouse;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-22
 */
public interface DepartmentService extends IService<Department> {
    List<Department> getSelectResult(Map<String,Object> map);
    List<Department> getAll();

    Department getOne(Integer departmentId);

    Department insertOrUpdateOne(Department department);
}
