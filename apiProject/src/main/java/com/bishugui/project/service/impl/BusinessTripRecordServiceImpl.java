package com.bishugui.project.service.impl;

import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.bishugui.project.pojo.personnel.BusinessTripRecord;
import com.bishugui.project.mapper.personnel.BusinessTripRecordMapper;
import com.bishugui.project.service.personnel.BusinessTripRecordService;
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
public class BusinessTripRecordServiceImpl extends ServiceImpl<BusinessTripRecordMapper, BusinessTripRecord> implements BusinessTripRecordService {
    @Autowired
    BusinessTripRecordMapper mapper;
    @Override
    public List<BusinessTripRecord> getAll(Integer employeeId) {
        List<BusinessTripRecord> list = mapper.getAll(employeeId,null);
        return list;
    }

    @Override
    public BusinessTripRecord getOneRecord(Integer btId) {
        List<BusinessTripRecord> list = mapper.getAll(null,btId);
        return list.get(0);
    }

    @Override
    public Integer insertRecord(BusinessTripRecord businessTripRecord) {
        businessTripRecord.setApplyDate(LocalDateTime.now());
        businessTripRecord.setStartDate(BasicUtils.stringTransformLocalDateTime(businessTripRecord.getStartDateTemp()));
        businessTripRecord.setEndDate(BasicUtils.stringTransformLocalDateTime(businessTripRecord.getEndDateTemp()));
        int insert = mapper.insert(businessTripRecord);
        return businessTripRecord.getBtId();
    }

    @Override
    public String examineRecord(BusinessTripRecord businessTripRecord) {
        businessTripRecord.setApprovalDate(LocalDateTime.now());
        int i = mapper.updateById(businessTripRecord);
        return BasicUtils.getAppointLocalDateTimeToString(businessTripRecord.getApprovalDate());
    }
}
