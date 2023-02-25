package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import lombok.Getter;

/**
 * 金额处理策略枚举
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
public enum MoneyConvertStrategyEnum {
    PENNY_2_YUAN(new Penny2YuanMoneyConvertStrategy())
    ,YUAN_2_PENNY(new Yuan2PennyMoneyConvertStrategy())
    ;
    @Getter
    private MoneyConvertStrategy moneyConvertStrategy;

    MoneyConvertStrategyEnum(MoneyConvertStrategy moneyConvertStrategy) {
        this.moneyConvertStrategy = moneyConvertStrategy;
    }
}
