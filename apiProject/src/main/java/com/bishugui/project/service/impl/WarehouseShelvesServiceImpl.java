package com.bishugui.project.service.impl;

import com.bishugui.project.pojo.warehouse.WarehouseShelves;
import com.bishugui.project.mapper.warehouse.WarehouseShelvesMapper;
import com.bishugui.project.service.warehouse.WarehouseShelvesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bishugui
 * @since 2021-05-18
 */
@Service
public class WarehouseShelvesServiceImpl extends ServiceImpl<WarehouseShelvesMapper, WarehouseShelves> implements WarehouseShelvesService {
    private List<Integer> shelvesIdList = null;
    @Autowired
    WarehouseShelvesMapper warehouseShelvesMapper;
    @Override
    public List<WarehouseShelves> getAllWarehouseShelves(Integer warehouseId) {
        //查询出某仓库下的树形结构
        List<WarehouseShelves> shelvesList = warehouseShelvesMapper.getAllShelvesByWarehouse(warehouseId);
        return shelvesList;
    }

    @Override
    public WarehouseShelves insertOrUpdateShelves(WarehouseShelves warehouseShelves) {
        warehouseShelves.setUpdateDate(LocalDateTime.now());
        if(warehouseShelves.getShelvesId() != null && warehouseShelves.getShelvesId() > 0){
            int i = warehouseShelvesMapper.updateById(warehouseShelves);
        }else{
            warehouseShelvesMapper.insert(warehouseShelves);
        }
        return warehouseShelves;
    }

    @Override
    public String deleteWarehouseShelves(Integer shelvesId) {
        //1，查询出所有子货架的id和名称
        List<WarehouseShelves> shelvesList = warehouseShelvesMapper.getAllSonShelvesById(shelvesId);

        //2，将所有子货架的id提取出并放到集合中
        shelvesIdList = new ArrayList<>();
        extractShelvesId(shelvesList.get(0));
        System.out.println("============shelvesList=============");
        shelvesIdList.forEach(System.out::println);

        //3，判断是否库房中时候含有在该货架的物品


        //4,删除所有查出来的货架

        return "删除成功";
    }

    public void extractShelvesId(WarehouseShelves shelves){
        shelvesIdList.add(shelves.getShelvesId());
        if(shelves.getShelvesList() == null) return;
        for(WarehouseShelves warehouseShelves : shelves.getShelvesList()){
            extractShelvesId(warehouseShelves);
        }
    }
}
