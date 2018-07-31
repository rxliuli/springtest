package common.test;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * web 单元测试
 * 如果你只需要测试单个 Controller 就尽量使用这个, 测试运行速度上有明显的提升
 * 注: 这个测试基类只能测试注入 Controller 的 url, 如果你测试其他的 Controller 则会发生错误
 * {@code java.lang.AssertionError: Range for response status value 404}
 *
 * @author rxliuli by 2018/5/23 1:43
 */
public abstract class BaseWebUnitTest<Controller> extends BaseWebTest {
    /**
     * 要进行测试的 Controller Bean
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected Controller controller;

    @Before
    public void setupMockMvc() {
        //初始化MockMvc对象
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .build();
        restMockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setViewResolvers(new StandaloneMvcTestViewResolver())
                .alwaysExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .alwaysExpect(status().isOk())
                .build();
    }

    /**
     * 这个类是用于测试时使用的替换内置的视图解析器的类
     * <p>
     * 主要原因在于内置的资源视图解析器会检查视图路径循环依赖的问题(或许是 SpringTest 的 BUG...)
     * 通俗点解决的问题就是: 你的 RequestMapping url 与 view(视图名) 相同时 SpringTest 就会给出一些不是很友好的错误
     * {@code javax.servlet.ServletException: Circular view path [index]: would dispatch back to the current handler URL [/index] again. Check your ViewResolver setup! (Hint: This may be the result of an unspecified view, due to default view name generation.)}
     */
    class StandaloneMvcTestViewResolver extends InternalResourceViewResolver {

        public StandaloneMvcTestViewResolver() {
            super();
        }

        @Override
        protected AbstractUrlBasedView buildView(final String viewName) throws Exception {
            final InternalResourceView view = (InternalResourceView) super.buildView(viewName);
            // prevent checking for circular view paths
            view.setPreventDispatchLoop(false);
            return view;
        }
    }
}
