package hello.servlet.web.Frontcontroller.v3;

import hello.servlet.web.Frontcontroller.ModelView;

import java.util.Map;

public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap);
}
