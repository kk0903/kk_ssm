package com.ssm.service;

import com.ssm.domain.SysLog;

import java.util.List;

public interface ISysLogService {

    public void save(SysLog sysLog) throws Exception;

    public List<SysLog> findAll() throws Exception;
}
