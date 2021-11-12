package com.ssm.service;

import com.ssm.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public interface IUserService extends UserDetailsService {
    public List<UserInfo> findAll() throws Exception;

    public void save(UserInfo userInfo) throws Exception;

    public UserInfo findById(String id) throws Exception;

    public void AddRoleToUser(String userId, String[] ids) throws Exception;
}
