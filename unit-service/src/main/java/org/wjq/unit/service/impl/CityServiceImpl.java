package org.wjq.unit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wjq.unit.bo.City;
import org.wjq.unit.dao.CityDao;
import org.wjq.unit.service.CityService;

/**
 * @Program: unit-test-project
 * @Description: 城市服务实现类
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Service
public class CityServiceImpl implements CityService {


    @Autowired
    private CityDao cityDao;


    @Override
    public City getCity(long cityId) {
        return cityDao.selectById(cityId);
    }


    @Override
    public City getCityWithCopy(long cityId) {
        City city = cityDao.selectById(cityId);
        City r = new City();
        r.setId(city.getId());
        r.setName(city.getName());
        r.setOperator(city.getOperator());
        r.setCtime(city.getCtime());
        r.setUtime(city.getUtime());
        return r;


    }
}
