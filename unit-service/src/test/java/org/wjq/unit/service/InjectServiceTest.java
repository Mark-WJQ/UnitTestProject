package org.wjq.unit.service;

import org.assertj.core.util.Lists;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.wjq.unit.bo.City;
import org.wjq.unit.bo.Sku;
import org.wjq.unit.service.impl.InjectServiceImpl;
import org.wjq.unit.service.impl.OtherService;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class InjectServiceTest {


    @InjectMocks
    private InjectService injectService = new InjectServiceImpl();

    @Mock
    private CityService cityService;

    @Mock
    private SkuService skuService;

    @Mock
    private OtherService otherService;


    @Test
    @DisplayName("获取城市sku")
    void calCitySku() {
        long cityId = 10;
        City city = new City();
        city.setId(cityId);
        doReturn(city).when(cityService).getCity(cityId);
        injectService.calCitySku(cityId);
        verify(otherService, times(1)).doSomethingNoReturn(city);

        verify(otherService, times(1)).
                doSomethingNoReturn(ArgumentMatchers.argThat((it) -> {
                    return it.getId() == city.getId() && it.getName().equals(city.getName());
                }));

    }

    /**
     * 参数化测试
     *
     * @param skuList
     * @param cityId
     * @param city
     */
    @DisplayName("根据城市id获取SKU列表")
    @ParameterizedTest  //与注解Test互斥
    @MethodSource(value = "argProvider")
    void querySkuByCityId(List<Sku> skuList, long cityId, City city) {
        doReturn(city).when(cityService).getCity(cityId);

        doReturn(skuList).when(otherService).querySkuByCity(ArgumentMatchers.argThat((it) ->
                it.getId() == city.getId() && it.getName().equals(city.getName())
        ));
        List<Sku> rList = injectService.querySkuByCityId(cityId);
        Assertions.assertIterableEquals(skuList, rList);
    }

    static Stream<Arguments> argProvider() {
        return Stream.of(
                Arguments.of(Lists.newArrayList(new Sku(1L, "1"), new Sku(2L, "2")), 1L, new City(1L, "北京")),
                Arguments.of(Lists.newArrayList(new Sku(1L, "1"), new Sku(2L, "2")), 2L, new City(2L, "上海"))
        );
    }
}