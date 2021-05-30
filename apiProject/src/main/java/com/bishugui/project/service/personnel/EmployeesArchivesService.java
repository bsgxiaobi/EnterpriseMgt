package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.Employees;
import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.pojo.personnel.EmployeesArchives;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
public interface EmployeesArchivesService extends IService<EmployeesArchives> {
    List<EmployeesArchives> getAll();

    EmployeesArchives getOne(Integer employeeId);

    Boolean insert(EmployeesArchives archives);

    Boolean update(EmployeesArchives archives);
}
