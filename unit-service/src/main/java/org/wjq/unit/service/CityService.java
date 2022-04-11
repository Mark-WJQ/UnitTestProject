package org.wjq.unit.service;

import org.wjq.unit.bo.City;

/**
 * @Program: unit-test-project
 * @Description: A服务接口
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
public interface CityService {


    City getCity(long cityId);


    City getCityWithCopy(long cityId);
}
