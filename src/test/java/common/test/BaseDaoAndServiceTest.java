package common.test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rxliuli by 2018/5/23 1:42
 */
public abstract class BaseDaoAndServiceTest<BaseBean> extends BaseTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    protected BaseBean base;
}
