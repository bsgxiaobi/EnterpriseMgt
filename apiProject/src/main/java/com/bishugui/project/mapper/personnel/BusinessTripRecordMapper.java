package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.ApplyLeaveRecord;
import com.bishugui.project.pojo.personnel.BusinessTripRecord;
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
public interface BusinessTripRecordMapper extends BaseMapper<BusinessTripRecord> {
    List<BusinessTripRecord> getAll(Integer employeeId, Integer id);
}
