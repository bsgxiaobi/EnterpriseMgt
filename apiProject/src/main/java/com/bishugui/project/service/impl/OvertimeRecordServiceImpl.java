package com.bishugui.project.service.impl;

import com.bishugui.project.pojo.personnel.BusinessTripRecord;
import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.mapper.personnel.OvertimeRecordMapper;
import com.bishugui.project.service.personnel.OvertimeRecordService;
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
public class OvertimeRecordServiceImpl extends ServiceImpl<OvertimeRecordMapper, OvertimeRecord> implements OvertimeRecordService {
    @Autowired
    OvertimeRecordMapper mapper;
    @Override
    public List<OvertimeRecord> getAll(Integer employeeId) {
        List<OvertimeRecord> list = mapper.getAll(employeeId,null);
        return list;
    }

    @Override
    public OvertimeRecord getOneRecord(Integer btId) {
        List<OvertimeRecord> list = mapper.getAll(null,btId);
        return list.get(0);
    }

    @Override
    public Integer insertRecord(OvertimeRecord overtimeRecord) {
        overtimeRecord.setApplyDate(LocalDateTime.now());
        overtimeRecord.setStartDate(BasicUtils.stringTransformLocalDateTime(overtimeRecord.getStartDateTemp()));
        int insert = mapper.insert(overtimeRecord);
        return overtimeRecord.getOrId();
    }

    @Override
    public String examineRecord(OvertimeRecord overtimeRecord) {
        overtimeRecord.setApprovalDate(LocalDateTime.now());
        int i = mapper.updateById(overtimeRecord);
        return BasicUtils.getAppointLocalDateTimeToString(overtimeRecord.getApprovalDate());
    }

}
