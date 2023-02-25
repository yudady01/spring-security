package cn.jpanda.demo.manager.configuration.log.handler.filters;

/**
 * @author HanQi [Jpanda@aliyun.com]
 * @version 1.0
 * @since 2020/6/8 10:14:43
 */
public class NoneFilter implements Filter {
    @Override
    public boolean filter(String str) {
        return false;
    }
}
