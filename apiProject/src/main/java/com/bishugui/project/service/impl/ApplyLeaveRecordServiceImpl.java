package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.bishugui.project.mapper.personnel.ApplyLeaveRecordMapper;
import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.bishugui.project.service.personnel.ApplyLeaveRecordService;
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
 * @since 2021-05-22
 */
@Service
public class ApplyLeaveRecordServiceImpl extends ServiceImpl<ApplyLeaveRecordMapper, ApplyLeaveRecord> implements ApplyLeaveRecordService {
    @Autowired
    ApplyLeaveRecordMapper mapper;
    @Override
    public List<ApplyLeaveRecord> getAll(Integer employeeId) {
        List<ApplyLeaveRecord> list = mapper.getAll(employeeId,null);
        return list;
    }

    @Override
    public ApplyLeaveRecord getOneRecord(Integer alrId) {
        List<ApplyLeaveRecord> list = mapper.getAll(null,alrId);
        return list.get(0);
    }

    @Override
    public Integer insertRecord(ApplyLeaveRecord applyLeaveRecord) {
        applyLeaveRecord.setApplyDate(LocalDateTime.now());
        applyLeaveRecord.setStartDate(BasicUtils.stringTransformLocalDateTime(applyLeaveRecord.getStartDateTemp()));
        applyLeaveRecord.setEndDate(BasicUtils.stringTransformLocalDateTime(applyLeaveRecord.getEndDateTemp()));
        int insert = mapper.insert(applyLeaveRecord);
        return applyLeaveRecord.getAlrId();
    }

    @Override
    public String examineRecord(ApplyLeaveRecord applyLeaveRecord) {
        applyLeaveRecord.setApprovalDate(LocalDateTime.now());
        int i = mapper.updateById(applyLeaveRecord);
        return BasicUtils.getAppointLocalDateTimeToString(applyLeaveRecord.getApprovalDate());
    }
}
