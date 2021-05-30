package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bishugui.project.pojo.personnel.AttendanceRecord;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-22
 */
public interface ApplyLeaveRecordService extends IService<ApplyLeaveRecord> {
    List<ApplyLeaveRecord> getAll(Integer employeeId);

    ApplyLeaveRecord getOneRecord(Integer alrId);

    Integer insertRecord(ApplyLeaveRecord applyLeaveRecord);

    String examineRecord(ApplyLeaveRecord applyLeaveRecord);
}
