package org.wjq.unit.service;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wjq.unit.bo.City;
import org.wjq.unit.dao.CityDao;
import org.wjq.unit.service.impl.CityServiceImpl;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {


    @InjectMocks
    private CityService cityService = new CityServiceImpl();

    @Mock
    private CityDao cityDao;


    @Test
    void getCity() {
        // mock的city与响应结果city是同一个对象
        City city = new City();
        city.setId(100L);
        city.setName("北京");
        doReturn(city).when(cityDao).selectById(100L);
        City r = cityService.getCity(100L);
        MatcherAssert.assertThat(r, Matchers.equalTo(city));
        MatcherAssert.assertThat(BigDecimal.valueOf(10.0), Matchers.comparesEqualTo(BigDecimal.TEN));


        MatcherAssert.assertThat(r, Matchers.equalTo(city));

        Assertions.assertEquals(city, r);
    }

    @Test
    void getCityWithCopy() {
        // 中间有一次复制，不是同一个对象，也没有重写equals，所以直接验证相等会报异常
        City city = new City();
        city.setId(100L);
        city.setName("北京");
        doReturn(city).when(cityDao).selectById(100L);
        City r = cityService.getCityWithCopy(100L);
        //Assertions.assertEquals(city, r);

        // MatcherAssert.assertThat()

        //比较属性
        Assertions.assertAll(() -> {
            Assertions.assertEquals(city.getId(), r.getId());
            Assertions.assertEquals(city.getName(), r.getName());
        });

        //或者 直接比较toString，前提是重写toString()
        Assertions.assertEquals(city.toString(), r.toString());
    }
}