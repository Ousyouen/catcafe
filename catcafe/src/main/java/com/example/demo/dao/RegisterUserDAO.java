package com.example.demo.dao;

import com.example.demo.model.RegisterUserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户信息表数据访问层
 */
@Mapper
public interface RegisterUserDAO {

    /**
     * 添加用户信息
     *
     * @param registerVO
     * @return
     */
    @Insert("insert into tab_user(name,email,password,phone,create_time,update_time,del_flag) " +
            "values(#{name},#{email},#{password},#{phone},#{createTime},#{updateTime},#{delFlag})")
    Integer registerUser(RegisterUserVO registerVO);

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     */
    @Select("select * from tab_user where name = #{name}")
    RegisterUserVO getUserInfoByName(String name);

}
