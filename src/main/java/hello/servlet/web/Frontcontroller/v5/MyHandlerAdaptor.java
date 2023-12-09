package hello.servlet.web.Frontcontroller.v5;

import hello.servlet.web.Frontcontroller.ModelView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;

public interface MyHandlerAdaptor {
    boolean supprots(Object handler);
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SerialException, IOException;
}
