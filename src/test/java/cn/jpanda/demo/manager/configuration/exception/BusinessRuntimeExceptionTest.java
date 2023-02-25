package cn.jpanda.demo.manager.configuration.exception;

import cn.jpanda.demo.manager.configuration.exception.ecodes.GlobalECode;
import org.junit.Test;

public class BusinessRuntimeExceptionTest {

    @Test
    public void exception() {
        BusinessRuntimeException exception = BusinessRuntimeException.exception(GlobalECode.INVALID_PARAMETER, new RuntimeException("123"), "name", "message");
        assert "无效参数[name]：message".equals(exception.getErrorMessage());

    }
}