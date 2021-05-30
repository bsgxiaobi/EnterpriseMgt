package com.bishugui.project.mapper.finance;

import com.bishugui.project.pojo.finance.VoucherItem;
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
 * @since 2021-05-24
 */
@Repository
public interface VoucherItemMapper extends BaseMapper<VoucherItem> {
    List<VoucherItem> getAllByParams(Map<String,Object> map);
}
