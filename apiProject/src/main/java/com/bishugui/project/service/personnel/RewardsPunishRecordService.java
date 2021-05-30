package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;
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
public interface RewardsPunishRecordService extends IService<RewardsPunishRecord> {
    List<RewardsPunishRecord> getAll(Integer employeeId);

    RewardsPunishRecord getOneRecord(Integer id);

    Integer insertRecord(RewardsPunishRecord record);

    String revokeRecord(RewardsPunishRecord record);
}
