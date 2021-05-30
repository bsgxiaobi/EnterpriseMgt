package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.customer.CustomerInfo;
import com.bishugui.project.pojo.personnel.Recruit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
public interface RecruitService extends IService<Recruit> {
    List<Recruit> getSelectResult(Map<String,Object> map);
    List<Recruit> getAll(Integer departmentId);

    Recruit getOneRecord(Integer id);

    Integer insertRecord(Recruit record);

    String updateRecord(Recruit record);
}
