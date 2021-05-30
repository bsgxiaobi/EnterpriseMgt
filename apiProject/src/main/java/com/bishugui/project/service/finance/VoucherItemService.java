package com.bishugui.project.service.finance;

import com.bishugui.project.pojo.finance.VoucherItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-24
 */
public interface VoucherItemService extends IService<VoucherItem> {
    List<VoucherItem> getAll(Integer id);

    List<VoucherItem> getAllByParams(Map<String,Object> map);
}
