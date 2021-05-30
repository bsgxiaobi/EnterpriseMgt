package com.bishugui.project.mapper.personnel;

import com.bishugui.project.pojo.personnel.Recruit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-23
 */
@Repository
public interface RecruitMapper extends BaseMapper<Recruit> {
    List<Recruit> getSelectResult(Map<String, Object> map);

    List<Recruit> getAll(Integer departmentId, Integer id);
}
