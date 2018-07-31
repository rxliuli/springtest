package common.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ref.SuperClassRefUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基本的 web 测试
 * 自定义测试类最好不要继承这个
 * 一般选择子类 BaseWebUnitTest/BaseWebIntegratedTest 即可
 *
 * @author rxliuli
 * @date 2018/6/9
 */
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:/spring/spring-mvc.xml")
public abstract class BaseWebTest extends BaseTest {
    @Autowired
    protected WebApplicationContext context;
    /**
     * 基本的用户 MockMvc
     */
    protected MockMvc mockMvc;
    /**
     * 用于 rest 服务的特化 MockMvc
     */
    protected MockMvc restMockMvc;

    /**
     * 用于反序列化对象方便进行断言
     */
    protected ObjectMapper objectMapper = new ObjectMapper()
            //配置在序列化时忽略 null 的字段
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    protected <T extends Serializable> MultiValueMap<String, String> param2Map(T param) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        Map<String, List<String>> map = SuperClassRefUtil.getAllDeclaredField(param.getClass()).stream()
                .collect(Collectors.toMap(Field::getName, field -> {
                    Object fieldValue = SuperClassRefUtil.getFieldValue(param, field.getName());
                    return Collections.singletonList(
                            fieldValue == null ? null : String.valueOf(fieldValue)
                    );
                }));
        multiValueMap.putAll(map);
        return multiValueMap;
    }
}
