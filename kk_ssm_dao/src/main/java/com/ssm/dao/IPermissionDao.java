package com.ssm.dao;

import com.ssm.domain.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPermissionDao {
    @Select("select * from permission where id in (select permissionId from role_permission where roleId=#{roleId})")
    public List<Permission> findByRoleId(String roleId);

    @Select("select * from permission")
    public List<Permission> findAll() throws Exception;

    @Insert("insert into permission (permissionName,url) values (#{permissionName},#{url})")
    public void save(Permission permission) throws Exception;


}
