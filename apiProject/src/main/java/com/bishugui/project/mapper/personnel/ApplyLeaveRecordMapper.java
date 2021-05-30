package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bishugui.project.pojo.personnel.AttendanceRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-22
 */
@Repository
public interface ApplyLeaveRecordMapper extends BaseMapper<ApplyLeaveRecord> {
    List<ApplyLeaveRecord> getAll(Integer employeeId,Integer id);
}
