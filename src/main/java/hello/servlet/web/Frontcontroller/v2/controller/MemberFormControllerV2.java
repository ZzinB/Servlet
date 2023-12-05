package hello.servlet.web.Frontcontroller.v2.controller;

import hello.servlet.web.Frontcontroller.MyView;
import hello.servlet.web.Frontcontroller.v1.ControllerV1;
import hello.servlet.web.Frontcontroller.v2.ControllerV2;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return new MyView("/WEB-INF/views/new-form.jsp");

    }
}
