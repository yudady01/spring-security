package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import org.springframework.util.StringUtils;

import java.text.DecimalFormat;

/**
 *  抽象的金额转换类，实现了公共方法的支持，主要提供了数据精度处理相关的API工子类使用
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 * @param <S> 原始类型
 * @param <T> 目标类型
 */
public  abstract class AbstractMoneyConvertStrategy<S,T> implements MoneyConvertStrategy<S,T> {
    /**
     * 格式
     * @see DecimalFormat
     */
    protected static final String FORMAT_PREFIX="######0";
    protected static final String ZERO="0";
    protected DecimalFormat generatorDecimalFormat(Money money){
        String format=money.decimalFormat();
        if (StringUtils.isEmpty(format)){
            // 按照精度生成格式
            format=appendZero(money.precision());
        }
        return new DecimalFormat(format);
    }
    protected String appendZero(int zeroCount){
        StringBuilder stringBuilder=new StringBuilder(FORMAT_PREFIX);
        if (zeroCount<=0){
            return stringBuilder.substring(0,stringBuilder.length()-1);
        }
        stringBuilder.append(".");
        for (int i=0;i<zeroCount;i++){
            stringBuilder.append(ZERO);
        }
        return stringBuilder.toString();
    }
}
