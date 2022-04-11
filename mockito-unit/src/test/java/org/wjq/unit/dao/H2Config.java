package org.wjq.unit.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @Program: unit-test-project
 * @Description: h2配置
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Component
@SpringBootTest(classes = {DataSourceAutoConfiguration.class, MybatisConfig.class})
@MapperScan(basePackages = "org.wjq.unit.dao")
@TestPropertySource(locations = "classpath:application.properties")
public abstract class H2Config {


}
