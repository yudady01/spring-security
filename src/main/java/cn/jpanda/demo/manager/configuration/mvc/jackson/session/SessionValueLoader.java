package cn.jpanda.demo.manager.configuration.mvc.jackson.session;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Objects;

public class SessionValueLoader {
    public static <T> T load(String key){
        HttpSession session=((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
      return (T) session.getAttribute(key);
    }
}
