package com.bishugui.project.mapper.finance;

import com.bishugui.project.pojo.finance.SingleAp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
@Repository
public interface SingleApMapper extends BaseMapper<SingleAp> {
    List<SingleAp> getAll(String id);
}
