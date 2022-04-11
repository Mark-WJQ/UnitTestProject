package org.wjq.unit.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;

import org.wjq.unit.bo.City;
import org.wjq.unit.bo.Sku;
import org.wjq.unit.service.impl.InjectServiceImpl;
import org.wjq.unit.service.impl.OtherService;
import spock.lang.Specification

import java.util.stream.Stream;

import static org.mockito.Mockito.doReturn;

class InjectServiceTest extends Specification {

    @Collaborator
    private CityService cityService = Mock();

    private SkuService skuService = Mock();

    @Subject
    // @InjectMocks
    InjectServiceImpl injectService

    @Collaborator
    // 类似于Mockito的@Mock
    private OtherService otherService = Mock();


    // private InjectService injectService = new InjectServiceImpl(cityService:cityService,skuService:skuService,otherService:otherService);


    def calCitySku() {
        given:
        long cityId = 10;
        City city = new City();
        city.setId(cityId);
        cityService.getCity(cityId) >> city
        when:
        injectService.calCitySku(cityId);
        then:
        1 * otherService.doSomethingNoReturn({City it ->
            it.getId() == city.getId()&&it.getName()==it.getName()
        })
    }

    /**
     * 参数化测试
     *
     * @param skuList
     * @param cityId
     * @param city
     */

    def querySkuByCityId() {
        given:
        cityService.getCity(cityId) >> city
        and:
        otherService.querySkuByCity({ City it ->
            it.getId() == city.getId() && it.getName().equals(city.getName())
        }) >> skuList

        when:
        List<Sku> rList = injectService.querySkuByCityId(cityId);
        then:
        Assertions.assertIterableEquals(skuList, rList);

        where:
        cityId || city               | skuList
        1L     || new City(1L, "北京") | [new Sku(1L, "1"), new Sku(2L, "2")]
        2L     || new City(2L, "上海") | [new Sku(1L, "1"), new Sku(2L, "2")]
    }
}