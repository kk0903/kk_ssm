package com.ssm.dao;

import com.ssm.domain.Member;
import com.ssm.domain.Orders;
import com.ssm.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdersDao {

    @Select("select * from orders")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "orderNum", property = "orderNum"),
            @Result(column = "orderTime", property = "orderTime"),
            @Result(column = "orderStatus", property = "orderStatus"),
            @Result(column = "peopleCount", property = "peopleCount"),
            @Result(column = "payType", property = "payType"),
            @Result(column = "orderDesc", property = "orderDesc"),
            @Result(column = "productId", property = "product", one = @One(select = "com.ssm.dao.IProductDao.findById"))
    })
    public List<Orders> findAll() throws Exception;


    @Select("select * from orders where id = #{ordersId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "orderNum", property = "orderNum"),
            @Result(column = "orderTime", property = "orderTime"),
            @Result(column = "orderStatus", property = "orderStatus"),
            @Result(column = "peopleCount", property = "peopleCount"),
            @Result(column = "payType", property = "payType"),
            @Result(column = "orderDesc", property = "orderDesc"),
            @Result(column = "productId", property = "product", javaType = Product.class, one = @One(select = "com.ssm.dao.IProductDao.findById")),
            @Result(column = "memberId", property = "member", javaType = Member.class, one = @One(select = "com.ssm.dao.IMemberDao.findById")),
            @Result(column = "id", property = "travellers", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.ITravellerDao.findByOrdersId"))
    })
    public Orders findById(String ordersId) throws Exception;
}
