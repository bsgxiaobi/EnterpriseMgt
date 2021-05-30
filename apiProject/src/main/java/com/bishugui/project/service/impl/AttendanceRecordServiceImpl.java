package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.bishugui.project.mapper.personnel.AttendanceRecordMapper;
import com.bishugui.project.service.personnel.AttendanceRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2021-05-22
 */
@Service
public class AttendanceRecordServiceImpl extends ServiceImpl<AttendanceRecordMapper, AttendanceRecord> implements AttendanceRecordService {
    @Autowired
    AttendanceRecordMapper mapper;
    @Override
    public List<AttendanceRecord> getAll(Integer employeeId) {
        List<AttendanceRecord> list = mapper.getAll(employeeId);
        return list;
    }

    @Override
    public AttendanceRecord representRecord(AttendanceRecord attendanceRecord) {
        attendanceRecord.setRepresentDate(LocalDateTime.now());
        int i = mapper.updateById(attendanceRecord);
        return attendanceRecord;
    }

    @Override
    public AttendanceRecord changeRecord(AttendanceRecord attendanceRecord) {
        attendanceRecord.setAgentDate(LocalDateTime.now());
        int i = mapper.updateById(attendanceRecord);
        return attendanceRecord;
    }
}
