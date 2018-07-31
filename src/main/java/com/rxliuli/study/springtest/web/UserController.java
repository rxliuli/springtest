package com.rxliuli.study.springtest.web;

import com.rxliuli.study.springtest.dao.UserDao;
import com.rxliuli.study.springtest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    /**
     * 获取用户信息
     *
     * @param id 用户 id
     * @return 用户对象信息
     */
    @GetMapping("/user/{id}")
    public User get(@PathVariable("id") Integer id) {
        return userDao.get(id);
    }

    /**
     * 获取全部的用户列表
     *
     * @return 全部的用户列表
     */
    @PostMapping("/user/listForAll")
    public List<User> listForAll() {
        return userDao.listForAll();
    }
}
