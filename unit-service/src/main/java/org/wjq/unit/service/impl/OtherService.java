package org.wjq.unit.service.impl;

import org.springframework.stereotype.Component;
import org.wjq.unit.bo.City;
import org.wjq.unit.bo.Sku;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: unit-test-project
 * @Description: 其他服务
 * @Author: wangjianqiang07
 * @Date: 2022/4/9
 **/
@Component
public class OtherService {


    public void doSomethingNoReturn(City city) {

    }


    public List<Sku> querySkuByCity(City city) {

        return new ArrayList<>();
    }


}
