package org.wjq.unit.service;

import org.wjq.unit.bo.Sku;

import java.util.List;

/**
 * @Program: unit-test-project
 * @Description: 注入服务
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
public interface InjectService {


    void calCitySku(long city);


    List<Sku> querySkuByCityId(long cityId);


}
