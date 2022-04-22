package com.example.test.aop;

import com.example.test.category.entity.Category;
import com.example.test.category.repository.categoryRepository;
import com.example.test.user.entity.Users;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Aspect
@Component
public class LoginCheckAspect {

    @Autowired
    categoryRepository categoryrepository;

    @Before("@annotation(com.example.test.aop.LoginType)")
    public void verifyUserSession() throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        verifyUserSession(session);
    }

    private void verifyUserSession(HttpSession session) throws Exception {

        Users users = (Users) session.getAttribute("LOGIN_MEMBER");

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        if (users == null) {
            response.sendRedirect("/");
        }
        else {
            List<Category> CList = categoryrepository.findAll();
            session.setAttribute("CList",CList);

            session.setAttribute("username", users.getUsername());
            session.setAttribute("role", users.getRole());
        }
    }
}
