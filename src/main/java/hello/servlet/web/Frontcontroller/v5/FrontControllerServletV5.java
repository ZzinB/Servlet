package hello.servlet.web.Frontcontroller.v5;

import hello.servlet.web.Frontcontroller.ModelView;
import hello.servlet.web.Frontcontroller.MyView;
import hello.servlet.web.Frontcontroller.v3.ControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.Frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.Frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.Frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.Frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import hello.servlet.web.Frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // ControllerV3, ControllerV4 같은 인터페이스 대신 아무 값이나 받을 수 있는 Object로 변경되었다.
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerApaters = new ArrayList<>();

    // 생성자는 핸들러 매핑과 어댑터를 초기화
    public FrontControllerServletV5(){
        initHandlerMappingMap();

        initHandlerAdapters();
    }
    private void initHandlerMappingMap() {
        //V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());

    }
    private void initHandlerAdapters() {
        handlerApaters.add(new ControllerV3HandlerAdapter());
        handlerApaters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //handler 호출
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //HandlerAdapter 가져옴
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        //handel 호출
        ModelView mv = adapter.handle(request, response, handler);

        //viewResolver호출
        String viewName = mv.getViewName();
        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    // handlerMappingMap에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    // handler를 처리할 수 있는 어댑터를 adapter.supports(handler)를 통해 찾음
    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerApaters) {
            if (adapter.supprots(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
