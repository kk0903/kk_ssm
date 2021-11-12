package com.ssm.service;

import com.ssm.domain.Permission;

import java.util.List;

public interface IPermissionService {
    public List<Permission> findAll() throws Exception;

    public void save(Permission permission) throws Exception;

}
