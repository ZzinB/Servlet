package hello.servlet.web.Frontcontroller.v3;

import hello.servlet.web.Frontcontroller.ModelView;
import hello.servlet.web.Frontcontroller.MyView;
import hello.servlet.web.Frontcontroller.v2.ControllerV3;
import hello.servlet.web.Frontcontroller.v2.controller.MemberFormControllerV3;
import hello.servlet.web.Frontcontroller.v2.controller.MemberListControllerV3;
import hello.servlet.web.Frontcontroller.v2.controller.MemberSaveControllerV3;
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
        Map<String, String> paramMap = createParamMap(HttpServletRequest request);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName();//논리이름 new-form
        // "/WEB-INF/views/new-form.jsp"
        MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");

        view.render(request, response);
    }

    private static Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}