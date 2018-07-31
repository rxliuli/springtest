package com.rxliuli.study.springtest.dao;

import com.rxliuli.study.springtest.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
//加载 Spring 配置文件
@ContextConfiguration(locations = "classpath:/spring/spring-context.xml")
//使用 SpringJUnit4ClassRunner 来运行 test
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoSpringTest {
    /**
     * 直接注入 UserDao 就好了
     */
    @Autowired
    private UserDao userDao;

    @Test
    public void get() {
        int id = 1;
        User result = userDao.get(id);
        //断言 id 和 get id 相同
        assertThat(result)
                .extracting(User::getId)
                .contains(id);
    }

    @Test
    public void listForAll() {
        List<User> userList = userDao.listForAll();
        //断言不为空
        assertThat(userList)
                .isNotEmpty();
    }
}