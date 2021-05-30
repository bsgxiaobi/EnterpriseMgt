package com.bishugui.project.service.impl;

import com.bishugui.project.pojo.personnel.EmployeesArchives;
import com.bishugui.project.mapper.personnel.EmployeesArchivesMapper;
import com.bishugui.project.service.personnel.EmployeesArchivesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Service
public class EmployeesArchivesServiceImpl extends ServiceImpl<EmployeesArchivesMapper, EmployeesArchives> implements EmployeesArchivesService {
    @Autowired
    EmployeesArchivesMapper mapper;

    @Override
    public List<EmployeesArchives> getAll() {
        return mapper.selectList(null);
    }

    @Override
    public EmployeesArchives getOne(Integer employeeId) {
        return mapper.selectById(employeeId);
    }

    @Override
    public Boolean insert(EmployeesArchives archives) {
        archives.setUpdateDate(LocalDateTime.now());
        archives.setContractStart(BasicUtils.stringTransformLocalDateTime(archives.getContractStartTemp()));
        archives.setJoinHandlingDate(LocalDateTime.now());
        int insert = mapper.insert(archives);
        return true;
    }

    @Override
    public Boolean update(EmployeesArchives archives) {
        archives.setUpdateDate(LocalDateTime.now());
        int i = mapper.updateById(archives);
        return true;
    }

}
