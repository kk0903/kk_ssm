package com.ssm.service;

import com.ssm.domain.Permission;
import com.ssm.domain.Role;

import java.util.List;


public interface IRoleService {

    public List<Role> findAll() throws Exception;

    public void save(Role role) throws Exception;

    public List<Role> findOtherRoleById(String id) throws Exception;

    public Role findById(String id) throws Exception;

    public List<Permission> findOtherPermission(String roleId) throws Exception;

    public void addPermissionToRole(String roleId, String[] permissionIds) throws Exception;
}
