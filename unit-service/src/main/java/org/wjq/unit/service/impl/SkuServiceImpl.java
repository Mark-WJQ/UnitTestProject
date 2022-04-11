package org.wjq.unit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wjq.unit.bo.Sku;
import org.wjq.unit.dao.SkuDao;
import org.wjq.unit.service.SkuService;

/**
 * @Program: unit-test-project
 * @Description: SKU服务实现类
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Service
public class SkuServiceImpl implements SkuService {


    @Autowired
    private SkuDao skuDao;


    @Override
    public Sku getSku(long skuId) {

        Sku sku = skuDao.selectById(skuId);
        Sku r = new Sku();
        r.setId(sku.getId());
        r.setName(sku.getName());
        r.setOperator(sku.getOperator());
        r.setPic(sku.getPic());
        r.setCtime(sku.getCtime());
        r.setUtime(sku.getUtime());
        return r;

    }
}
