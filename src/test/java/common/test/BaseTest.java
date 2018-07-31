package common.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 简单的测试基类
 * <BaseBean> 需要自动注入的 Bean 类型
 *
 * @author rxliuli
 * @date 2018/7/31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-context*.xml")
@Transactional
@Rollback
public abstract class BaseTest<BaseBean> {
    /**
     * 自动注入的 Bean
     */
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected BaseBean base;
}