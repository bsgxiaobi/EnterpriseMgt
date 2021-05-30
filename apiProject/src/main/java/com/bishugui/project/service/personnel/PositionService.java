package com.bishugui.project.service.personnel;

import com.bishugui.project.pojo.personnel.Position;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
public interface PositionService extends IService<Position> {
    List<Position> getAll(Integer departmentId);

    List<Position> getAllPosition(Integer departmentId);

    Position insertOrUpdate(Position position);
}
