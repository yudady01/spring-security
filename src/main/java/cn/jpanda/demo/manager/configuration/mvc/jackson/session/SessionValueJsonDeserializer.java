package cn.jpanda.demo.manager.configuration.mvc.jackson.session;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.SneakyThrows;

public class SessionValueJsonDeserializer extends JsonDeserializer<Object> implements ContextualSerializer {

    private SessionValue sessionValue;

    private BeanProperty property;

    public SessionValueJsonDeserializer() {
    }

    public SessionValueJsonDeserializer(SessionValue sessionValue, BeanProperty property) {
        this.sessionValue = sessionValue;
        this.property = property;
    }

    @Override
    @SneakyThrows
    public Object deserialize(JsonParser p, DeserializationContext ctxt) {
        // 从上下文获取数据，此处模拟
        try {
            String sessionKey=sessionValue.key();
            Object object=SessionValueLoader.load(sessionKey);
            if (object==null){
                if (sessionValue.required()){
                    throw new NotFindSessionValueRuntimeException();
                }

            }
            return object;
        }catch (Exception e){
            sessionValue.exceptionHandler().newInstance().handler(e);
        }
        return null;
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property==null){
            return prov.findNullValueSerializer(property);
        }
        // 获取注解
        SessionValue sessionValue=property.getAnnotation(SessionValue.class);
        if (sessionValue!=null){
            return new SessionValueJsonSerializer(sessionValue,property);
        }
        return prov.findValueSerializer(property.getType(),property);

    }
}
