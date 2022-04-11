package org.wjq.unit.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wjq.unit.bo.Sku;
import org.wjq.unit.dao.SkuDao;
import org.wjq.unit.service.impl.SkuServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static sun.rmi.transport.TransportConstants.Return;

@ExtendWith(MockitoExtension.class)
class SkuServiceTest {


    @InjectMocks
    private SkuService skuService = new SkuServiceImpl();

    @Mock
    private SkuDao skuDao;

    @Test
    void getSku() {
        // 重写sku的equals与hashCode 方法
        Sku sku = new Sku();
        sku.setId(10L);
        sku.setName("sku");

        doReturn(sku).when(skuDao).selectById(10L);
        Sku r = skuService.getSku(10L);
        Assertions.assertEquals(sku, r);


    }
}