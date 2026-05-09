package org.zerock.ch3.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.zerock.ch3.dto.MemberDTO;
import org.zerock.ch3.service.MemberService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"})
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("Login check filter...");

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        HttpSession session = req.getSession();

        // 로그인 여부 확인
        if(session.getAttribute("loginInfo") != null) {
            chain.doFilter(request, response);
            return;
        }

        // session에 loginInfo가 없다면, 쿠키를 확인
        Cookie cookie = findCookie(req.getCookies(), "remember-me");

        // 1. 세션에도 없고, 쿠키도 없다면 그냥 로그인으로 진행
        if(cookie == null) {
            resp.sendRedirect("/login");
            return;
        }

        // 2. 쿠키가 존재하는 상황이라면 데이터베이스 확인 후 회원 정보를 세션에 추가
        log.info("Cookie 존재 확인");
        String uuid = cookie.getValue();

        try {
            // 1) 데이터베이스 조회
            MemberDTO memberDTO = MemberService.INSTANCE.getByUUID(uuid);

            log.info("쿠키의 값으로 조회한 사용자의 정보 : " + memberDTO);
            if(memberDTO == null) {
                throw new Exception("Cookie value is not valid");
            }

            // 2) 회원 정보를 세션에 추가
            session.setAttribute("loginInfo", memberDTO);
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("/login");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if(cookies == null || cookies.length == 0) {
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(name))
                .findFirst();

        return result.orElse(null);
    }
}
