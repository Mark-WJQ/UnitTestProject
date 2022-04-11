package org.wjq.unit.dao;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired
import org.wjq.unit.bo.Sku

import javax.annotation.Resource;

class SkuDaoTest extends H2Config {

    @Resource
    private SkuDao skuDao;


    def "根据id查询sku"() {
        expect:
        Sku sku = skuDao.selectById(12345L);
        Sku exp = new Sku();
        exp.setId(12345L);
        exp.setName("aaa");
        exp.setPic("https://images.app.goo.gl/j7vvPVbCFtZzNwHF9");
        exp.setOperator("wjq");
        Assertions.assertEquals(exp.getId(), sku.getId());
        Assertions.assertEquals(exp.getName(), sku.getName());
        Assertions.assertEquals(exp.getPic(), sku.getPic());
        Assertions.assertEquals(exp.getOperator(), sku.getOperator());

    }


    void insert() {
        Sku sku = new Sku();
        sku.setName("sku");
        sku.setOperator("wjq");
        sku.setPic("https://images.app.goo.gl/mTXkgA4effUYopNB8");
        int count = skuDao.insert(sku);
        System.out.println(sku + "--------");
        Assertions.assertEquals(1, count);
        System.out.println("======" + skuDao.selectById(sku.getId()));
    }




}