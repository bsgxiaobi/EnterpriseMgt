package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface AttendanceRecordMapper extends BaseMapper<AttendanceRecord> {
    List<AttendanceRecord> getAll(Integer employeeId);
}
