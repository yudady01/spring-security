package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import java.text.DecimalFormat;

/**
 * 元转分策略
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
public class Yuan2PennyMoneyConvertStrategy extends AbstractMoneyConvertStrategy<Number,Long> {
    @Override
    public Long convert(Number number,Money money) {
        return Long.valueOf(generatorDecimalFormat(money).format(number.doubleValue()*100));
    }
    @Override
    protected DecimalFormat generatorDecimalFormat(Money money) {
        return new DecimalFormat(FORMAT_PREFIX);
    }
}
