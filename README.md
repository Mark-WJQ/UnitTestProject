### 单测的一些个人实践

##### 背景
1. 现在团队对单测的覆盖率有一定的要求，自己在最近的项目迁移中做了比较多的单测实践，自己在写单测的过程中也踩了不少坑，总结了一些常用的单测技巧，把这些记录下来，分享出来。
2. 由于单测本身是一个比较宏大的话题，本文不涉及单元测试的定义及单测的宏观讨论，只是对单测的技术实践，默认一个public方法即为一个测试单位。
##### 目的
1. 分享自己写单测的一些经验，帮助大家避开一些坑
2. 同时也帮助一些对于不熟悉如何写单测的同学，提供一些实践的案例
3. 团队内部交流一下写单测的一些经验，互通有无，共同提高单测的质量、效率
4. 使用[spock](https://spockframework.org/spock/docs/2.1/all_in_one.html#_spock_primer) 与 junit&[mockito](https://site.mockito.org/) 做示例，说明两种框架在测试同一种场景时的不同解决方案
##### 内容
###### 单测的一些概念
1. 单测步骤
    1. 给定一些数据、条件-- given
        * 构造参数、mock
    2. 执行待测试的方法 -- when
    3. 验证测试结果 -- then
        * 被测试对象响应结果
        * 被测试对象中的状态
        * 被测试对象中与外部交互
2. mock、stub 、spy
>[Mocks Aren't Stubs](https://martinfowler.com/articles/mocksArentStubs.html)
1. mock 模拟对象响应、记录模拟对象的调用情况、验证模拟对象
2. stub 主要功能之一是模拟对象响应
3. spy 在调用时默认调用真实方法，主要用途是在部分mock,在同一个类中，有两个public方法A、B， A调用B，当我们只想想对B mock 时可以使用spy
```java
cityService = spy(CityServiceImpl.class);

    doReturn(city).when(cityService).getCity(100L);
    City r = cityService.getCityWithCopy(100L);

    ```

###### 示例
1. 一个简单的测试用例,被测试方法无依赖,验证一个文件后缀是否通过
```java
// junit
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ImageValidator {

    @Test
    public void validateFileExtension() {
       String file = 'some.jpeg'
       boolean isValid = true;
       assertEquals(isValid, validate(file));
    }
}

```
```groovy
//spock
import spock.lang.Specification
import spock.lang.Unroll

@Title("Testing file extension validation method")
class ImageValidatorShould extends Specification {

    @Unroll
    def "validate extension of #fileToValidate"() {
        when: "validator checks filename"
        def isValid = validate fileToValidate
       then: "return appropriate result"
       isValid == expectedResult

       where: "input files are"
       fileToValidate || expectedResult
       'some.jpeg'    || true
       'some.jpg'     || true
       'some.tiff'    || false
       'some.bmp'     || true
       'some.png'     || false
    }
}
```
2. 被测试方法中加入依赖，被测试方法中有依赖时涉及到依赖mock与注入
```java
//junit 自己不能mock，需要借助于第三方框架（Mockito 或 EasyMock等），Spring推荐使用 Mockito
@RunWith(MockitoJUnitRunner.class)
class CityServiceTest {


    @InjectMocks
    private CityService cityService = new CityServiceImpl();

    @Mock // Mockito.mock(CityDao.class)
    private CityDao cityDao;


    @Test
    void getCity() {
        // mock的city与响应结果city是同一个对象
        long cityId = 100L;
        City city = new City();
        city.setId(cityId);
        city.setName("北京");

        doReturn(city).when(cityDao).selectById(cityId);


        City r = cityService.getCity(cityId);


        Assertions.assertEquals(city, r);
    }


```

    [spock注解注入](https://github.com/marcingrzejszczak/spock-subjects-collaborators-extension)

    ```groovy

    //spock 中自带mock功能，同时groovy语言可以通过构造方法或直接赋值解决依赖，也有第三方提供注解注入
    class CityServiceTest extends Specification{

        @Collaborator
        private CityDao cityDao = Mock();

        @Subject //new CityServiceImpl(cityDao:cityDao)
        private CityServiceImpl cityService;


        def "获取城市"() {
            // mock的city与响应结果city是同一个对象
            given:"给定参数"
            long cityId = 100L;

            City city = new City();
            city.setId(cityId);
            city.setName("北京");
            and:"模拟调用"
            cityDao.selectById(cityId) >> city;

            when:
            City r = cityService.getCity(cityId);
            then:
            city ==  r
        }
    }
    ```

3. 复杂对象的比较

   当我们在使用基本类型时，”==“ 可以解决两个是否相等的问题，但是在使用自己定义或基本类型以外的类定义的对象时，”==“ 解决不了两个对象是否相等的问题。在单测中主要有两个地方需要对对象进行比较：1. 调用mock方法时参数匹配；2. 响应对象的比较

    1. 参数
        * 直接使用any
        * 重写equals()
        * 使用mockito/spock 提供的方法
        ```java
        //mockito ArgumentMatchers.argThat()
        doReturn(skuList).when(otherService).querySkuByCity(ArgumentMatchers.argThat((it) ->
                it.getId() == city.getId() && it.getName().equals(city.getName())
        ));

        // 验证是否调用过相应的方法
        verify(otherService, times(1)).
                doSomethingNoReturn(ArgumentMatchers.argThat((it) -> {
                    return it.getId() == city.getId() && it.getName().equals(city.getName());
                }));

        //spock 使用groovy闭包
         otherService.querySkuByCity({ City it ->
            it.getId() == city.getId() && it.getName().equals(city.getName())
        }) >> skuList


        1 * otherService.doSomethingNoReturn({City it ->
            it.getId() == city.getId()&&it.getName()==it.getName()
        })
        ```
    2. 响应对象的比较
        * 挑出重点信息比较
        * 重写equals()
        * 重写toString()，直接比较string
        * 定制比较工具，eg: 利用反射比较两个类中相同字段的值， 适用于自己不能或不方便重写的类
        * 使用mockito/spock 提供的方法
        ```java

        // --- junit ----
        // assertThat 是junit中最常用比较响应结果api 方法，可以看一下 Matchers 中其他方法或自己重新 Matcher
         MatcherAssert.assertThat(BigDecimal.valueOf(10.0), Matchers.comparesEqualTo(BigDecimal.TEN));

        // 重写equals()后，直接比较两个列表，不过需要两个列表中元素的顺序一样
         Assert.assertEquals(skuList, rList);

         //重写toString()，直接比较string
        Assert.assertEquals(city.toString(), r.toString());


        // --- spock 中常用的方式是使用 ’==‘，groovy 默认调用 equals
        // verifyAll()、with()，以及上述在junit中提到的方式
        with(pc) {
            vendor == "Sunny"
            clockRate >= 2333
            ram >= 406
            os == "Linux"
        }

        verifyAll(pc) {
            vendor == "Sunny"
            clockRate >= 2333
            ram >= 406
            os == "Linux"
        }

        ```

4. 参数化测试，不同参数触发不同的场景
    1. spock 灵活，在测试方法体内，where 打码块就可以指定参数测试，更加符合思维逻辑
    ```groovy
    import spock.lang.Specification
    import spock.lang.Unroll

    @Title("Testing file extension validation method")
    class ImageValidatorShould extends Specification {

        @Unroll
        def "validate extension of #fileToValidate"() {
            when: "validator checks filename"
            def isValid = validate fileToValidate
           then: "return appropriate result"
           isValid == expectedResult

           where: "input files are"
           fileToValidate || expectedResult
           'some.jpeg'    || true
           'some.jpg'     || true
           'some.tiff'    || false
           'some.bmp'     || true
           'some.png'     || false
        }
    }
    ```

    2. junit 参数化测试时测试参数与测试用例是分离的，需要使用专门的测试类与注解标明
        ```java

        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.junit.runners.Parameterized;
        import org.junit.runners.Parameterized.Parameters;
        import java.util.Collection;

        import static java.util.Arrays.asList;
        import static org.junit.Assert.assertEquals;

        @RunWith(Parameterized.class)
        public class ImageValidator {

            @Parameters
            public static Collection<Object[]> data() {
                return asList(new Object[][]{
                    {"some.jpeg", true},
                    {"some.jpg", true},
                    {"some.tiff", false},
                    {"some.bmp", true},
                    {"some.png", false}
                });
            }

            private String file;
            private boolean isValid;

            public ImageValidator(String input, boolean expected) {
                file = input;
                isValid = expected;
            }

            @Test
            public void validateFileExtension() {
                assertEquals(isValid, validate(file));
            }
        }

        ```

5. 在一次测试中被多次调用，不同的入参与出参要求
    * 不对入参做判断，按照调用顺序响应
    * 满足条件的入参即可响应，入参与响应没有严格的对应关系，范围对应
    * 入参与响应有严格的对应关系
```java
//----- spock
subscriber.receive(_) >>> ["ok", "fail", "ok"] >> { throw new InternalError() } >> "ok"

cityService.getCity({long it -> if(it == 1 || it == 2) true;}) >>> [city,city1]

// 根据入参判断返回不同的响应结果，在mockito 中需要分成两个语句实现
subscriber.receive(_) >> { String message -> message.size() > 3 ? "ok" : "fail" }

//精确模拟
cityService.getCity(cityId1) >> city1
cityService.getCity(cityId2) >> city2



// ------ mockito
doReturn(city1,city2).when(cityService).getCity(any());

doReturn(city1,city2).when(cityService).getCity(ArgumentMatchers.argThat((it) -> it == cityId));

// 模拟两次
doReturn(city1).when(cityService).getCity(cityId1);
doReturn(city2).when(cityService).getCity(cityId2);

// 第一次调用什么也不做，第二次调用抛出异常
doNothing().
doThrow(new RuntimeException()).
when(mock).someVoidMethod();

```


6. 异常模拟
```groovy
 //--- junit4 或者使用@Rule 或在代码中catch以后再进行相关比较
 @Test(expected = Exception.class)

 public class FooTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void doStuffThrowsIndexOutOfBoundsException() {
        Foo foo = new Foo();

        exception.expect(IndexOutOfBoundsException.class);
        foo.doStuff();
    }
}


//--- junit5
@Test
void testException(){
    doThrow(new RuntimeException("测试异常")).when(cityService).getCity(101L);
    RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class,() -> injectService.querySkuByCityId(101L));
    Assertions.assertEquals("测试异常",runtimeException.getMessage());
}

// --- spock
def '测试异常'{
   given:
   def e= new DelegateException();
   citDao.selectById(_) >> throw e;
   when:
   cityService.getCity(_) >>> [city,city]
   then:
   def de = thrown(DelegateException.class)
   de == e
}

```

7.  多线程模拟
    1. 线程池是mock的，没有实际线程启动，只是模拟runnable 调用，串行调用，这样验证业务逻辑的正确性是没有问题的
    ```groovy
    // --- Mockito

    @Mock
    Executor asyncExecutor;

    Mockito.doAnswer((invocation) ->{
        ((Runnable)invocation.getArguments()[0]).run();
        return null;
    }).when(asyncExecutor).execute(any(Runnable.class));

    //----spock

    Executor asyncExecutor = Mock();

    asyncExecutor.execute(_) >> { Runnable it -> it.run() }

    ```
    2. 如果需要模拟并发调用，模拟多线程冲突，需要在环境中启动真实线程池，根据实际情况模拟

8. 对象初始化
    * java必须严格按照语法进行new，如果没有专门的初始化方法，需要调用大量的get、set 方法，进行强制语法检查，好处是在运行前就可以发现问题，ide会提示
    * groovy 作为动态语言，语法比较灵活，在new的时候可以直接指定字段值、也可以使用跟java相同的方法，坏处是在可能字段可能拼错，不能及时检查出来，只能在运行时才可以发现
    * 在写单测时，可能会有比较多的对象需要初始化，有的类有比较多的属性，属性之间可能还会有对应关系，并且可能在不同的单测中使用，如果一个一个属性设置的话会非常耗费时间跟精力，此时我们可以把对象的初始化工作抽取出来，做一个对象工厂，这样对于使用者来说，可以不用太关注对象是如何初始化的，但是设值的工作并没有减少，此时可以将对象属性以json或XML格式保存在classpath下，通过工具类读取文件，生成对应的对象，根据需要再修改本次测试涉及到的属性。而文件内容可以通过日志或上下游提供直接获取。此时文件相当于是一个源对象。
9. 测试结构
    * spock 在测试用例中按照BDD的模式规定了用例结构(given-when-then)，风格统一，在进行review时容易发现问题，有迹可循，上手比较快,同时可以在每个阶段有文档注释，便于理解
    * junit&mockito 没有结构层次，需要开发同学自己在写的时候注意，可能每位同学的编写风格都不一致
10. 在写单测时尽量避免使用any，使用准确的数据有助于提高单测的准确性
    * eg: 一个类有两个int字段，在方法中只是用了其中一个，此时就有可能使用错，如果直接用anyInt()的话，根本验证不出来
    ```groovy
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
            // 应该使用参数p.a
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
        1 * s.plusA(_)  // 通过
        1 * s.plusA(param.a)  //失败

    }
    ```
11. 私有、静态、final 方法
    如果涉及到这些方法的mock，大概率是代码结构不合理，此时应尽量重构，如果不方便的话可以引入[powermock](https://github.com/powermock/powermock)
    1. 提取成公共方法
    2. 提取成公共类

###### Spring 环境下单测
1. 在spring环境中执行单测，不可避免的需要启动spring上下文，spring初始化的时候会初始化一些对象和连接外部中间件，eg:数据库、配置中心、rpc等，这些连接会很耗时，还有可能连接不通，导致测试失败。初始化的对象在本次单测中大部分是无用的，增加耗时，还会导致单测不稳定。所以在spring中单测时，需要注意两点，1. 只加载使用到的对象；2. 使用本地中间件或mock代替外部中间件
2. 测试mybatis数据库操作，使用h2 代替mysql

```java


@Configuration
class MybatisConfig {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

}


@RunWith(SpringRunner.class)
@Rollback
@EnableTransactionManagement
@ContextConfiguration(classes  = {DataSourceAutoConfiguration.class, MybatisConfig.class})
@TestPropertySource(locations ={"classpath:h2/h2.datasource.properties"} )
@MapperScan(basePackages = {"com.dao"})
public abstract class H2TransactionalTestBase {
    @InjectMocks
    private SkuService skuService = new SkuServiceImpl();

    @MockBean
    private SkuDao skuDao;
}


@Rollback
@EnableTransactionManagement
@SpringBootTest(classes = {DataSourceAutoConfiguration.class, MybatisConfig.class},properties = {"classpath:h2/h2.datasource.properties"})
@MapperScan(basePackages = {"com.dao"})
public abstract class H2TransactionalTestBase {
    @InjectMocks
    private SkuService skuService = new SkuServiceImpl();

    @MockBean
    private SkuDao skuDao;
}
```
```properties
#  结合Spring，直接配置Spring配置即可，只支持sql，简单 或使用注解@sql
# classpath:h2/h2.datasource.properties
spring.datasource.driver-class-name=org.h2.Driver
# 数据库初始化sql
spring.datasource.schema=classpath:h2/schema-h2.sql
spring.datasource.data= classpath:h2/data/*.sql
spring.datasource.url=jdbc:h2:mem:test;MODE=MYSQL;CASE_INSENSITIVE_IDENTIFIERS=TRUE
spring.datasource.username=root
spring.datasource.password=test
```

引入其他工具，需要自己写加载mybatis，数据格式比较灵活，xml、csv、sql



