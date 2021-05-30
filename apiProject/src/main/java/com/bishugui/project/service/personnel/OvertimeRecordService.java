package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.BusinessTripRecord;
import com.bishugui.project.pojo.personnel.OvertimeRecord;
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
public interface OvertimeRecordService extends IService<OvertimeRecord> {
    List<OvertimeRecord> getAll(Integer employeeId);

    OvertimeRecord getOneRecord(Integer btId);

    Integer insertRecord(OvertimeRecord overtimeRecord);

    String examineRecord(OvertimeRecord overtimeRecord);
}
