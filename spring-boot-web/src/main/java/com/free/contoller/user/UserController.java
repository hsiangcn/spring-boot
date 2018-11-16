package com.free.contoller.user;

import com.free.code.utils.ResultMessageUtils;
import com.free.dao.model.User;
import com.free.service.user.UsreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired(required = false)
     private UsreService usreService;

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getUser(@RequestBody User user) {
        logger.info(" 查询用户信息 ");
        User resUser = usreService.getUser(user.getId());
        return ResultMessageUtils.resultSuccessObject(resUser);
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody User user) {

        try {
            logger.info(" 新增用户信息 ");
            usreService.addUser(user);
        } catch (Exception ex ) {
            ex.printStackTrace();
            return ResultMessageUtils.resultError(ex.getMessage());
        }

        return ResultMessageUtils.resultSuccess();
    }
}
