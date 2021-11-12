package com.ssm.dao;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao {
    /*根据用户id查询所有角色*/
    @Select("select * from role where id in (select roleId from users_role where userId = #{userId})")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "roleName", property = "roleName"),
            @Result(column = "roleDesc", property = "roleDesc"),
            @Result(column = "id", property = "permissions", javaType = java.util.List.class, many = @Many(select = "com.ssm.dao.IPermissionDao.findByRoleId"))
    })
    public List<Role> findRoleByUserId(String userId) throws Exception;

    @Select("select * from role")
    public List<Role> findAll() throws Exception;

    @Insert("insert into role(roleName,roleDesc) values(#{roleName},#{roleDesc})")
    public void save(Role role) throws Exception;

    @Select("select * from role where id not in (select roleId from users_role where userId=#{id})")
    public List<Role> findOtherRoleById(String id) throws Exception;

    @Select("select * from role where id = #{id}")
    public Role findById(String id) throws Exception;

    @Select("select * from permission where id not in(select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findOtherPermission(String roleId) throws Exception;

    @Insert("insert into role_permission (roleId,permissionId) values(#{roleId},#{permissionId})")
    public void addPermissionToRole(@Param("roleId") String roleId, @Param("permissionId") String permissionId) throws Exception;
}
