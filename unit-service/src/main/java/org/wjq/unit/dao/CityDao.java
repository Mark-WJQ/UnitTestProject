package org.wjq.unit.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.wjq.unit.bo.City;

/**
 * @Program: unit-test-project
 * @Description: 城市sql
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Mapper
public interface CityDao {

    @Select("select * from city where id = #{id}")
    City selectById(Long id);


    @Insert(value = "insert into sku (name,operator) VALUES (#{name},#{operator})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(City sku);


}
