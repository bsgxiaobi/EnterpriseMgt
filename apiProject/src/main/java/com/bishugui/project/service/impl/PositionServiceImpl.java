package com.bishugui.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bishugui.project.pojo.personnel.Position;
import com.bishugui.project.mapper.personnel.PositionMapper;
import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.bishugui.project.service.personnel.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    private List<Integer> positionIdList = null;
    @Autowired
    PositionMapper mapper;

    @Override
    public List<Position> getAll(Integer departmentId) {
        //查询出某仓库下的树形结构
        List<Position> list = mapper.getAll(departmentId);
        return list;
    }

    @Override
    public List<Position> getAllPosition(Integer departmentId) {
        QueryWrapper<Position> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_id",departmentId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Position insertOrUpdate(Position position) {
        position.setUpdateDate(LocalDateTime.now());
        if(position.getPositionId() != null && position.getPositionId() > 0){
            int i = mapper.updateById(position);
        }else{
            int insert = mapper.insert(position);
        }
        return position;
    }
    public void extractPositionId(Position position){
        positionIdList.add(position.getPositionId());
        if(position.getPositionList() == null) return;
        for(Position position1 : position.getPositionList()){
            extractPositionId(position1);
        }
    }
}
