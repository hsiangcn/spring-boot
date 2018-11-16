package com.free.service.user.impl;

import com.free.dao.model.User;
import com.free.dao.model.UserExample;
import com.free.dao.user.UserMapper;
import com.free.service.user.UsreService;
import com.free.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UsreService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public User getUser(Integer id) {
        User user = (User)redisUtils.get("0001");
        if (user != null) {
            return user;
        }
//        User user = null;
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<User> userList = userMapper.selectByExample(example);
        if (!userList.isEmpty()) {
            user = userList.get(0);
        }
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean addUser(User user) throws Exception {
        logger.info("增加用户逻辑");
        int i = userMapper.insert(user);
        if ("888888".equals(user.getUserName())) {
            throw new RuntimeException("手动抛出异常，导致数据回滚");
        }

        if (i > 0) {
            logger.info("插入Redis");
            redisUtils.set("0001",user);
            return true;
        }
        return false;
    }
}
