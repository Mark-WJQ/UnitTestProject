package org.wjq.unit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wjq.unit.bo.City;
import org.wjq.unit.bo.Sku;
import org.wjq.unit.service.CityService;
import org.wjq.unit.service.InjectService;
import org.wjq.unit.service.SkuService;

import java.util.List;

/**
 * @Program: unit-test-project
 * @Description: 注入类测试
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Service
public class InjectServiceImpl implements InjectService {

    @Autowired
    private CityService cityService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private OtherService otherService;

    @Override
    public void calCitySku(long cityId) {
        City city = cityService.getCity(cityId);
        otherService.doSomethingNoReturn(city);
    }

    @Override
    public List<Sku> querySkuByCityId(long cityId) {
        City city = cityService.getCity(cityId);
        return otherService.querySkuByCity(city);
    }


}
