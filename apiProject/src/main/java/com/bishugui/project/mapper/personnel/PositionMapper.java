package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.Position;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;
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
public interface PositionMapper extends BaseMapper<Position> {
    //获取某个部门下的所有职位
    List<Position> getAll(Integer departmentId);

    //获取所有子职位
    List<Position> getAllSon(Integer positionId);
}
