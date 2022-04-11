package org.wjq.unit.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.wjq.unit.bo.Sku;

/**
 * @Program: unit-test-project
 * @Description: Sku
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
@Mapper
public interface SkuDao {


    @Select(value = "select * from sku where id = #{id}")
    Sku selectById(Long id);


    @Insert(value = "insert into sku (name,pic,operator) VALUES (#{name},#{pic},#{operator})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insert(Sku sku);

    @Select(value = "select count(*) from sku")
    int count();





}
