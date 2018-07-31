package com.rxliuli.study.springtest.dao;

import com.rxliuli.study.springtest.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
@Repository
public class UserDao {
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("sex"),
            rs.getInt("age")
    );
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据 id 获取一个对象
     *
     * @param id id
     * @return 根据 id 查询到的对象，如果没有查到则为 null
     */
    public User get(Integer id) {
        return jdbcTemplate.queryForObject("select * from user where id = ?", userRowMapper, id);
    }

    /**
     * 查询全部用户
     *
     * @return 全部用户列表
     */
    public List<User> listForAll() {
        return jdbcTemplate.query("select * from user", userRowMapper);
    }

    /**
     * 根据 id 删除用户
     *
     * @param id 用户 id
     * @return 受影响行数
     */
    public int deleteById(Integer id) {
        return jdbcTemplate.update("delete from user where id = ?", id);
    }
}
