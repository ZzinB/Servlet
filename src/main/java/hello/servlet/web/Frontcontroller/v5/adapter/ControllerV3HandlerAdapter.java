package hello.servlet.web.Frontcontroller.v5.adapter;

import hello.servlet.web.Frontcontroller.ModelView;
import hello.servlet.web.Frontcontroller.v3.ControllerV3;
import hello.servlet.web.Frontcontroller.v5.MyHandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supprots(Object handler) {
        return(handler instanceof ControllerV3);
    }
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //V3컨트롤러로 변경
        ControllerV3 controller = (ControllerV3) handler;
        Map<String, String> paramMap = createParamMap(request);
        //컨트롤러 process 호출
        ModelView mv = controller.process(paramMap);
        return mv;
    }
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
