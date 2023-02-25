package cn.jpanda.demo.manager.configuration.mvc.jackson.session;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.SneakyThrows;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class SessionValueJsonSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private SessionValue sessionValue;

    private BeanProperty property;

    public SessionValueJsonSerializer() {
    }

    public SessionValueJsonSerializer(SessionValue sessionValue, BeanProperty property) {
        this.sessionValue = sessionValue;
        this.property = property;
    }

    @Override
    @SneakyThrows
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        // 从上下文获取数据，此处模拟
        try {
            String sessionKey=sessionValue.key();
            Object object=SessionValueLoader.load(sessionKey);
            if (object==null){
                if (sessionValue.required()){
                    throw new NotFindSessionValueRuntimeException();
                }
                jsonGenerator.writeNull();
                return;
            }
            jsonGenerator.writeObject(object);
        }catch (Exception e){
            sessionValue.exceptionHandler().newInstance().handler(e);
        }
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
