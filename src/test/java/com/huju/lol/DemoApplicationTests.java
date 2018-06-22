package com.huju.lol;

import com.huju.lol.controller.HelloWorldController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;


/**
 * Created by v_huju on 2018/6/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
    }

    @Test
    public void testHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Spring Boot Hello World!")));
    }

    @Test
    public void testUserApi() throws Exception{
        RequestBuilder request = null;

        // get 获取所有用户列表
        request = get("/users/");
        mvc.perform(request).andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));

        // post 添加用户
        request = post("/users/")
                .param("id", "1")
                .param("name", "mengday")
                .param("age", "20");
        mvc.perform(request).andExpect(content().string(equalTo("success")));

        // get get all users
        request = get("/users/");
        mvc.perform(request).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"mengday\",\"age\":20}]")));


        // put 更新用户
        request = put("/users/1")
                .param("name", "mengday")
                .param("age", "28");
        mvc.perform(request).andExpect(content().string(equalTo("success")));

        // 查看某个用户的信息
        request = get("/users/1");
        mvc.perform(request).andExpect(content().string(equalTo("{\\\"id\\\":1,\\\"name\\\":\\\"mengday\\\",\\\"age\\\":28}")));

        // 删除用户信息
        request = delete("/users/1");
        mvc.perform(request).andExpect(content().string(equalTo("success")));

        request = get("/users/");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
    }
}
