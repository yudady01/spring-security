package cn.jpanda.demo.manager.configuration.mvc.jackson;

import cn.jpanda.demo.manager.configuration.mvc.jackson.session.SessionValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.Test;

public class SessionValueJsonSerializerTest {

    @Test
    @SneakyThrows
    public void serialize() {
        SV sv=new SV();
        sv.setName("panda");
        System.out.println(new ObjectMapper().writeValueAsString(sv));
    }
    @Data
    public static class SV{
        @SessionValue(key = "123")
        private String name;
    }
}