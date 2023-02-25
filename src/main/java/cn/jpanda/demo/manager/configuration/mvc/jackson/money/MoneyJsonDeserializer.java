package cn.jpanda.demo.manager.configuration.mvc.jackson.money;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;

/**
 * 反序列化处理注解
 * @author HanQi(Jpanda@aliyun.com)
 * @since  2019年12月5日11:25:55
 */
public class MoneyJsonDeserializer extends JsonDeserializer<Object> implements ContextualSerializer {
    private Money money;

    private BeanProperty beanProperty;

    public MoneyJsonDeserializer() {
    }

    public MoneyJsonDeserializer(Money money, BeanProperty beanProperty) {
        this.money = money;
        this.beanProperty = beanProperty;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return money.strategy().getMoneyConvertStrategy().convert(p.getNumberValue(),money);
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
