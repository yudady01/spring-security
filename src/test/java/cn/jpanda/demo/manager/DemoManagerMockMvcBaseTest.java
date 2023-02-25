package cn.jpanda.demo.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * MVC基础单元测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoManagerMockMvcBaseTest {
    /**
     * web应用上下文
     */
    @Resource
    protected WebApplicationContext webApplicationContext;


    protected MockMvc mockMvc;

    @Resource
    protected ObjectMapper objectMapper;


    @Before
    public void init() {
        mockMvc = getDefaultMockMvcBuilder().build();
    }

    /**
     * 获取MockMvc构建器
     *
     * @return MockMvc构建器
     */
    private DefaultMockMvcBuilder getDefaultMockMvcBuilder() {
        return doHandlerMockMvcBuilder(MockMvcBuilders.webAppContextSetup(webApplicationContext));
    }

    /**
     * 提供给子类复写的MockMvc构建器的扩展方法
     *
     * @param builder MockMvc构建器
     * @return MockMvc构建器
     */
    protected DefaultMockMvcBuilder doHandlerMockMvcBuilder(DefaultMockMvcBuilder builder) {
        return builder;
    }

    /**
     * 简单的公共方法，将对象装换位JSON字符串
     *
     * @param o 对象
     * @return JSON数据
     */
    protected String toJson(Object o) {
        try {
            return o == null ? "{}" : objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
            return "{}";
        }
    }

    /**
     * 简单断言请求是否成功
     *
     * @param resultActions 操作结果行为对象
     */
    @SneakyThrows
    protected void assertSuccess(ResultActions resultActions) {
        success(resultActions);
    }

    /**
     * 做简单的成功断言
     *
     * @param resultActions 操作结果行为对象
     * @return 操作结果行为对象
     */
    @SneakyThrows
    protected ResultActions success(ResultActions resultActions) {
        return resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", "").value(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", "").value("200"));
    }

    /**
     * 简单断言请求是否成功
     *
     * @param resultActions 操作结果行为对象
     */
    @SneakyThrows
    protected void assertFail(ResultActions resultActions) {
        fail(resultActions);
    }

    /**
     * 做简单的成功断言
     *
     * @param resultActions 操作结果行为对象
     * @return 操作结果行为对象
     */
    @SneakyThrows
    protected ResultActions fail(ResultActions resultActions) {
        return resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", "").value(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", "").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage", "").exists())
                ;
    }

    @SneakyThrows
    protected void log(ResultActions resultActions) {
        log.info(
                resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString()
        );
    }
}
