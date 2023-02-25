package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import java.text.DecimalFormat;

/**
 * 分转元
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
public class Penny2YuanMoneyConvertStrategy extends AbstractMoneyConvertStrategy<Number,Double> {
    @Override
    public Double convert(Number number,Money money) {
        return Double.valueOf(generatorDecimalFormat(money).format(number.doubleValue()/100));
    }


}
