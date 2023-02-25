package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * 序列化处理Money注解
 *
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
public class MoneyJsonSerializer  extends JsonSerializer<Number> implements ContextualSerializer {

    private Money money;

    private BeanProperty beanProperty;

    public MoneyJsonSerializer() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(money.strategy().getMoneyConvertStrategy().convert(value,money));
    }

    public MoneyJsonSerializer(Money money, BeanProperty beanProperty) {
        this.money = money;
        this.beanProperty = beanProperty;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property==null){
            return prov.findNullValueSerializer(property);
        }
        // 获取注解
        Money money=property.getAnnotation(Money.class);
        if (money!=null){
            return new MoneyJsonSerializer(money,property);
        }
        return prov.findValueSerializer(property.getType(),property);
    }
}
