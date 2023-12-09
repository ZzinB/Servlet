package hello.servlet.web.Frontcontroller.v5;

import hello.servlet.web.Frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.Frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // ControllerV3, ControllerV4 같은 인터페이스 대신 아무 값이나 받을 수 있는 Object로 변경되었다.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdaptor> handlerApators = new ArrayList<>();

    // 생성자는 핸들러 매핑과 어댑터를 초기화
    public FrontControllerServletV5(){
        handlerMappingMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v3/members", new MemberListControllerV3());

        handlerApators.add(new ControllerV3HandlerAdapter());
    }

}
