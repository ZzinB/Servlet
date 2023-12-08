package hello.servlet.web.Frontcontroller.v3;

import hello.servlet.web.Frontcontroller.ModelView;
import hello.servlet.web.Frontcontroller.MyView;
import hello.servlet.web.Frontcontroller.v3.ControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.Frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //paramMap
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();//논리이름 new-form
        // "/WEB-INF/views/new-form.jsp"
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);
    }

    //논리이름을 가지고 실제 물리적이름을 만들면서 myView를 반환하는 메소드
    // 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경
    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    // HttpServletRequest에서 파라미터 정보를 꺼내서 paramMap으로 반환
    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}