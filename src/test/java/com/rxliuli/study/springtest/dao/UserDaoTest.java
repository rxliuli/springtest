package com.rxliuli.study.springtest.dao;

import com.rxliuli.study.springtest.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void before() {
        //使用 spring xml 配置文件初始化 ApplicationContext
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/spring/spring-context.xml");
        //然后使用 ApplicationContext 获取 UserDao 的对象
        userDao = context.getBean(UserDao.class);
    }

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