package com.example.test.aop;

import com.example.test.category.mybatis.dto.CategoryDto;
import com.example.test.category.mybatis.mapper.boardCategoryMapper;
import com.example.test.coupang.jpa.entity.CateEntity;
import com.example.test.coupang.jpa.repository.CateRepository;
import com.example.test.coupang.mybatis.dto.CateDto;
import com.example.test.coupang.mybatis.mapper.CateMapper;
import com.example.test.user.mybatis.dto.UserDto;
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

    /*@Autowired
    categoryRepository categoryrepository;*/
    @Autowired
    boardCategoryMapper boardcategorymapper;

    @Autowired
    CateMapper catemapper;

    @Before("@annotation(com.example.test.aop.LoginType)")
    public void verifyUserSession() throws Exception {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        verifyUserSession(session);
    }

    private void verifyUserSession(HttpSession session) throws Exception {

        UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        if (users == null) {
            response.sendRedirect("/");
        }
        else {
            List<CategoryDto> CList = boardcategorymapper.findAll();
            session.setAttribute("CList",CList);

            List<CateDto> CateList = catemapper.findByDepth(0);
            session.setAttribute("CateList",CateList);
            List<CateDto> CateList1 = catemapper.findByDepth(1);
            session.setAttribute("CateList1",CateList1);
            List<CateDto> CateList2 = catemapper.findByDepth(2);
            session.setAttribute("CateList2",CateList2);
            List<CateDto> CateList3 = catemapper.findByDepth(3);
            session.setAttribute("CateList3",CateList3);

            session.setAttribute("username", users.getUsername());
            session.setAttribute("role", users.getRole());
        }
    }
}
