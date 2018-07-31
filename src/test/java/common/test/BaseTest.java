package common.test;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rxliuli by 2018/5/23 1:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/spring-context*.xml")
@Transactional
@Rollback
public abstract class BaseTest {
    /**
     * 设置单个测试的最大超时时间
     */
    @Rule
    public final Timeout globalTimeout = Timeout.seconds(3);
    /**
     * 设置抛出异常
     */
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    protected final Logger log = LoggerFactory.getLogger(getClass());
}
