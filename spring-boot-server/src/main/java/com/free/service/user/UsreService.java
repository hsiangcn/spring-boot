package com.free.service.user;

import com.free.dao.model.User;

public interface UsreService {

    public User getUser(Integer id);

    public Boolean addUser(User user) throws Exception;
}
