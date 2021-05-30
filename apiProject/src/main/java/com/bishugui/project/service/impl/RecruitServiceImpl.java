package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.personnel.Department;
import com.bishugui.project.pojo.personnel.Recruit;
import com.bishugui.project.mapper.personnel.RecruitMapper;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;
import com.bishugui.project.service.personnel.RecruitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bishugui.project.utils.BasicUtils;
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
 * @since 2021-05-23
 */
@Service
public class RecruitServiceImpl extends ServiceImpl<RecruitMapper, Recruit> implements RecruitService {
    @Autowired
    RecruitMapper mapper;

    @Override
    public List<Recruit> getSelectResult(Map<String, Object> map) {
        if(map.get("departmentId") == null || map.get("departmentId") == ""){
            map.put("departmentId",null);
        }
        if(map.get("startDate") == null || map.get("startDate") == ""){
            map.put("startDate",null);
            map.put("endDate",null);
        }
        return mapper.getSelectResult(map);
    }

    @Override
    public List<Recruit> getAll(Integer departmentId) {
        List<Recruit> list = mapper.getAll(departmentId,null);
        return list;
    }

    @Override
    public Recruit getOneRecord(Integer id) {
        List<Recruit> list = mapper.getAll(null,id);
        return list.get(0);
    }

    @Override
    public Integer insertRecord(Recruit record) {
        record.setApplyDate(LocalDateTime.now());
        int insert = mapper.insert(record);
        return record.getRecruitId();
    }

    @Override
    public String updateRecord(Recruit record) {
        record.setUpdateDate(LocalDateTime.now());
        int i = mapper.updateById(record);
        return BasicUtils.getAppointLocalDateTimeToString(record.getUpdateDate());
    }
}
