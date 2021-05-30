package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.AttendanceRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-22
 */
public interface AttendanceRecordService extends IService<AttendanceRecord> {
    List<AttendanceRecord> getAll(Integer employeeId);

    //申述记录
    AttendanceRecord representRecord(AttendanceRecord attendanceRecord);

    //变更记录
    AttendanceRecord changeRecord(AttendanceRecord attendanceRecord);

}
