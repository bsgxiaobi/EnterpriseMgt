package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.OvertimeRecord;
import com.bishugui.project.pojo.personnel.RewardsPunishRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Repository
public interface RewardsPunishRecordMapper extends BaseMapper<RewardsPunishRecord> {
    List<RewardsPunishRecord> getAll(Integer employeeId, Integer id);
}
