package com.bishugui.project.service.impl;

import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;
import com.bishugui.project.mapper.personnel.RewardsPunishRecordMapper;
import com.bishugui.project.service.personnel.RewardsPunishRecordService;
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
public class RewardsPunishRecordServiceImpl extends ServiceImpl<RewardsPunishRecordMapper, RewardsPunishRecord> implements RewardsPunishRecordService {
    @Autowired
    RewardsPunishRecordMapper mapper;
    @Override
    public List<RewardsPunishRecord> getAll(Integer employeeId) {
        List<RewardsPunishRecord> list = mapper.getAll(employeeId,null);
        return list;
    }

    @Override
    public RewardsPunishRecord getOneRecord(Integer id) {
        List<RewardsPunishRecord> list = mapper.getAll(null,id);
        return list.get(0);
    }

    @Override
    public Integer insertRecord(RewardsPunishRecord record) {
        record.setApplyDate(LocalDateTime.now());
        int insert = mapper.insert(record);
        return record.getRwrId();
    }

    @Override
    public String revokeRecord(RewardsPunishRecord record) {
        record.setUpdateDate(LocalDateTime.now());
        int i = mapper.updateById(record);
        return BasicUtils.getAppointLocalDateTimeToString(record.getUpdateDate());
    }
}
