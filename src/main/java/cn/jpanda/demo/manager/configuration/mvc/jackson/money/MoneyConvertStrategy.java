package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

/**
 * 金额转换策略类
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 * @param <source> 原始类型
 * @param <target> 目标类型
 */
public interface MoneyConvertStrategy<source,target> {
    /**
     * 进行价格转换
     * @param source 原始价格
     * @return 转换后的价格
     */
    target convert(source source,Money money);
}
