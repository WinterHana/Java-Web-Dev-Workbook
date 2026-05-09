package org.zerock.ch3.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.C;
import org.zerock.ch3.dto.MemberDTO;
import org.zerock.ch3.service.MemberService;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/login")
@Log4j2
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("login get.............");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("login post........");

        String mid = req.getParameter("mid");
        String mpw = req.getParameter("mpw");

        // 자동 로그인 체크 여부 확인 및 UUID 생성
        String auto = req.getParameter("auto");
        boolean rememberMe = auto != null && auto.equals("on");

        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);

            if (rememberMe) {
                String uuid = UUID.randomUUID().toString();

                log.info("Insert UUID : " + uuid);

                MemberService.INSTANCE.updateUuid(mid, uuid);
                memberDTO.setUuid(uuid);

                // remeber-me 쿠키 생성
                Cookie rememberCookie = new Cookie("remeber-me", uuid);
                rememberCookie.setMaxAge(60 * 60 * 24 * 7);
                rememberCookie.setPath("/");

                resp.addCookie(rememberCookie);
            }

            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO);
            resp.sendRedirect("/todo/list");

        } catch (Exception e) {
            resp.sendRedirect("/login?result=error");
        }
    }
}
