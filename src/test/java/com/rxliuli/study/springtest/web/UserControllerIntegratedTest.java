package com.rxliuli.study.springtest.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author rxliuli
 * @date 2018/7/31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-*.xml")
@Transactional
@Rollback
@WebAppConfiguration
public class UserControllerIntegratedTest {
    @Autowired
    private WebApplicationContext context;
    /**
     * 用于测试 API 的模拟请求对象
     */
    private MockMvc mockMvc;

    @Before
    public void before() {
        //这里把整个 WebApplicationContext 上下文都丢进去了，所以可以测试所有的 Controller
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }

    @Test
    public void testGet() throws Exception {
        //测试能够正常获取
        Integer id = 1;
        mockMvc.perform(
                //发起 get 请求
                get("/user/" + id)
        )
                //断言请求的状态是成功的(200)
                .andExpect(status().isOk())
                //断言返回对象的 id 和请求的 id 相同
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    public void listForAll() throws Exception {
        //测试正常获取
        mockMvc.perform(
                //发起 post 请求
                post("/user/listForAll")
        )
                //断言请求状态
                .andExpect(status().isOk())
                //断言返回结果是数组
                .andExpect(jsonPath("$").isArray())
                //断言返回数组不是空的
                .andExpect(jsonPath("$").isNotEmpty());
    }
}