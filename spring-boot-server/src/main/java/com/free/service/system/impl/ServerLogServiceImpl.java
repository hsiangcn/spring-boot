package com.free.service.system.impl;

import com.free.dao.model.ServerLog;
import com.free.dao.server.ServerLogMapper;
import com.free.service.system.ServerLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServerLogServiceImpl implements ServerLogService {

    private static final Logger logger = LoggerFactory.getLogger(ServerLogServiceImpl.class);

    @Autowired
    private ServerLogMapper serverLogMapper;

    @Async
    @Override
    public void saveServerLog(ServerLog serverLog) {
        serverLogMapper.insertSelective(serverLog);
    }
}
