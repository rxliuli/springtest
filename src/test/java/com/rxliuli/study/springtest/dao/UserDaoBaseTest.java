package com.rxliuli.study.springtest.dao;

import com.rxliuli.study.springtest.entity.User;
import common.test.BaseDaoAndServiceTest;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
public class UserDaoBaseTest extends BaseDaoAndServiceTest<UserDao> {
    @Test
    public void get() {
        int id = 1;
        User result = base.get(id);
        //断言 id 和 get id 相同
        assertThat(result)
                .extracting(User::getId)
                .contains(id);
    }

    @Test
    public void listForAll() {
        List<User> userList = base.listForAll();
        //断言不为空
        assertThat(userList)
                .isNotEmpty();
    }

    @Test
    public void deleteById() {
        int result = base.deleteById(1);
        assertThat(result)
                .isGreaterThan(0);
    }

    @Test
    public void deleteByIdForTransaction() {
        //这个仅仅是为了测试事物与自动回滚是否生效
        int result = base.deleteById(1);
        assertThat(result)
                .isGreaterThan(0);
    }
}