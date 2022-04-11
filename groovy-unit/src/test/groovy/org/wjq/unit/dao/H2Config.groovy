package org.wjq.unit.dao;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spock.lang.Specification;

/**
 * @Program: unit-test-project
 * @Description: h2配置
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@EnableTransactionManagement
@SpringBootTest(classes = [DataSourceAutoConfiguration.class, MybatisConfig.class])
@TestPropertySource(locations = "classpath:application.properties")
abstract class H2Config extends Specification{


}
