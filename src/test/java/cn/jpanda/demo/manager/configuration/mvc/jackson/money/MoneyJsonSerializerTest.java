package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;

public class MoneyJsonSerializerTest {

    @Test
    @SneakyThrows
    public void start(){
        Order order=new Order();
        order.setPenny2yuan(100123);//1001.23
        order.setYuan2penny(100.021);//10002
        System.out.println(new ObjectMapper().writeValueAsString(order));
    }
    @Data
    public static class Order{
        @Money(strategy = MoneyConvertStrategyEnum.PENNY_2_YUAN)
        public double penny2yuan;
        @Money(strategy = MoneyConvertStrategyEnum.YUAN_2_PENNY)
        public double yuan2penny;
    }

}