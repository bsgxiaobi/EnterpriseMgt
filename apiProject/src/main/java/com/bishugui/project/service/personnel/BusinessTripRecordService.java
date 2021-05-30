package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.BusinessTripRecord;
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
public interface BusinessTripRecordService extends IService<BusinessTripRecord> {
    List<BusinessTripRecord> getAll(Integer employeeId);

    BusinessTripRecord getOneRecord(Integer btId);

    Integer insertRecord(BusinessTripRecord businessTripRecord);

    String examineRecord(BusinessTripRecord businessTripRecord);
}
