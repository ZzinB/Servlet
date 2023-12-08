package hello.servlet.web.Frontcontroller.v4;

import hello.servlet.web.Frontcontroller.ModelView;
import hello.servlet.web.Frontcontroller.MyView;
import hello.servlet.web.Frontcontroller.v4.ControllerV4;
import hello.servlet.web.Frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.Frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.Frontcontroller.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        //모델 객체를 프론트 컨트롤러에서 생성해서 넘겨준다.
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        //뷰의 논리 이름을 직접 반환(이 값을 이용해서 실제 물리 뷰 찾음)
        MyView view = viewResolver(viewName);
        view.render(model, request, response);
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