package org.wjq.unit.service

import com.blogspot.toomuchcoding.spock.subjcollabs.Collaborator
import com.blogspot.toomuchcoding.spock.subjcollabs.Subject;

import org.wjq.unit.bo.City;
import org.wjq.unit.dao.CityDao
import org.wjq.unit.service.impl.CityServiceImpl;
import spock.lang.Specification;


class CityServiceTest extends Specification {

    @Collaborator
    private CityDao cityDao = Mock();

    @Subject
    private CityServiceImpl cityService;


    def "获取城市"() {
        // mock的city与响应结果city是同一个对象
        given: "给定参数"
        long cityId = 100L;

        City city = new City();
        city.setId(cityId);
        city.setName("北京");
        and: "模拟调用"
        cityDao.selectById(cityId) >> city;

        when:
        City r = cityService.getCity(cityId);
        then:
        city == r
    }


    class Param {
        int a;
        int b;
    }


    class S {
        int k = 10;

        void plusA(int a) {
            k += a;
        }
    }


    class S1 {
        int k = 10;
        S s;

        void invokeS(Param p) {

            s.plusA(p.b)
        }
    }


    def '测试用错参数'() {
        given:
        S s = Mock();
        Param param = new Param(a: 10, b: 11)
        and:
        S1 s1 = new S1(s: s)
        when:
        s1.invokeS(param)
        then:
        1 * s.plusA(_)
        1 * s.plusA(param.a)

    }


    /*  @Test
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


      @Test
      void getCityWithCopySpy() {
          // 中间有一次复制，不是同一个对象，也没有重写equals，所以直接验证相等会报异常
          City city = new City();
          city.setId(100L);
          city.setName("北京");

          cityService = spy(CityServiceImpl.class);

          doReturn(city).when(cityService).getCity(100L);
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
      }*/
}