package com.example.test.user.controller;

import com.example.test.aop.LoginType;
import com.example.test.user.common.securityUtils;
import com.example.test.user.entity.Users;
import com.example.test.user.repository.userRepository;
import com.example.test.user.service.userService;
import org.attoparser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import org.thymeleaf.exceptions.TemplateProcessingException;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class userController {

    @Autowired
    userService userservice;

    @Autowired
    userRepository userrepository;

    private static final String LOGIN_MEMBER = "LOGIN_MEMBER";

    // ROLE_MEMBER Controller------------------------------------------------------------------------------------------------------------------//

    @LoginType
    @RequestMapping(value = "/user/userDetail/{username}", method = RequestMethod.GET)
    public String userDetail(@PathVariable String username, Model model, HttpSession session) throws Exception {

        try {
            Users users = (Users) session.getAttribute("LOGIN_MEMBER");
            if (username.equals(users.getUsername())) {                  // 세션에 담긴 정보와 사용자 정보가 일치하는지
                model.addAttribute("users", userservice.userDetail(username));
                return "user/userDetail";                                // 다를 경우 조회 불가(다른 회원 정보 조회 불가능)
            } else {
                return "redirect:/home";
            }

        } catch (NullPointerException e1) {                              // 세션이 없는 경우
            return "login/signIn";
        }
    }

    @LoginType
    @RequestMapping(value = "/user/userUpdate/{username}", method = RequestMethod.GET)
    public String user(@PathVariable String username, Model model, HttpSession session) throws Exception {

        try {
            Users users = (Users) session.getAttribute("LOGIN_MEMBER");
            if (username.equals(users.getUsername())) {                  // 세션에 담긴 정보와 사용자 정보가 일치하는지
                model.addAttribute("users", userservice.userDetail(username));
                return "user/userUpdate";
            } else {
                return "redirect:/home";                                 // 다를 경우 조회 불가(다른 회원 정보 수정 불가능)
            }

        } catch (NullPointerException e1) {                              // 세션이 없는 경우
            return "login/signIn";
        }
    }

    @LoginType
    @RequestMapping(value = "/user/userUpdate/{username}", method = RequestMethod.POST)
    public String userUpdate(@PathVariable String username, Model model, HttpSession session, Users user) throws Exception {

        Users users = (Users) session.getAttribute("LOGIN_MEMBER");
        model.addAttribute("users", userservice.userDetail(username));

        if (user.getPw().equals("****") || securityUtils.sha256ToString(user.getPw()).equals(users.getPw())) {
            user.setPw(users.getPw());
        } else {
            user.setPw(securityUtils.sha256ToString(user.getPw()));
        }
        user.setEmail(user.getEmail());
        user.setRole(users.getRole());
        userrepository.save(user);

        return "redirect:/user/userDetail/{username}";
    }

    // ROLE_ADMIN Controller------------------------------------------------------------------------------------------------------------------//

    @LoginType
    @RequestMapping(value = "/user/userList/admin", method = RequestMethod.GET)
    public String userList_admin(Model model, @PageableDefault(size = 2) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String searchText) throws Exception {

        Page<Users> list = userservice.userPageList(searchText, pageable);

        int startPage = Math.max(1, list.getPageable().getPageNumber() - 4);
        int endPage = Math.min(list.getTotalPages(), list.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("list", list);
        model.addAttribute("num", list.getTotalElements());

        return "user/admin/userList_admin";
    }

    // 회원 상세 정보
    @LoginType
    @RequestMapping(value = "/user/userDetail/admin/{username}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userDetail_admin(@PathVariable("username") String username, Model model, HttpSession session, @RequestParam(required = false) Long page, @RequestParam(required = false) String search) throws Exception {

        session.setAttribute("uPage", page);
        session.setAttribute("uSearchText", search);

        try {
            Users user = (Users) session.getAttribute("LOGIN_MEMBER");
            if (user.getRole().equals("ROLE_ADMIN")) {                                               // 관리자 계정이 맞는지
                model.addAttribute("user", userservice.userDetail(username));
                return "user/admin/userDetail_admin";
            } else {
                return "redirect:/login/logout";                                                    // 관리자가 아닐 경우 조회 불가(다른 회원 정보 조회 불가능) // 강제종료 시키기
            }
        } catch (NullPointerException e) {
            return "login/signIn";
        }
    }

    // 회원 추가
    @LoginType
    @RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.GET)
    public String userWrite_admin(Model model) throws Exception {
        List<String> userList = userrepository.findUsername();
        model.addAttribute("userList", userList);
        return "user/admin/userWrite_admin";
    }

    @LoginType
    @RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.POST)
    public String userInsert_admin(Users user) throws Exception {


        System.out.println(user);
        String pw = securityUtils.sha256ToString(user.getPw());
        user.setPw(pw);
        userservice.userSave(user);

        return "redirect:/user/userList/admin";
    }

    // 회원 정보 수정
    @LoginType
    @RequestMapping(value = "/user/userUpdate/admin/{username}", method = RequestMethod.GET)
    public String userAdmin(@PathVariable String username, Model model, HttpSession session) throws Exception {

        try {
            Users users = (Users) session.getAttribute("LOGIN_MEMBER");
            if (users.getRole().equals("ROLE_ADMIN")) {                  // 관리자 계정이 맞는지
                model.addAttribute("users", userservice.userDetail(username));
                return "user/admin/userUpdate_admin";
            } else {
                return "redirect:/home";                                 // 다를 경우 조회 불가(다른 회원 정보 수정 불가능)
            }

        } catch (NullPointerException e1) {                              // 세션이 없는 경우
            return "login/signIn";
        }
    }

    @LoginType
    @RequestMapping(value = "/user/userUpdate/admin/{username}", method = RequestMethod.POST)
    public String userAdminUpdate(@PathVariable String username, Model model, HttpSession session, Users user) throws Exception {

        Users users = (Users) session.getAttribute("LOGIN_MEMBER");
        model.addAttribute("users", userservice.userDetail(username));

        if (user.getPw().equals("****") || securityUtils.sha256ToString(user.getPw()).equals(userservice.userDetail(username).getPw())) {
            user.setPw(users.getPw());
        } else {
            user.setPw(securityUtils.sha256ToString(user.getPw()));
        }

        user.setEmail(user.getEmail());
        user.setRole(user.getRole());

        userrepository.save(user);


        return "redirect:/user/userDetail/admin/{username}";
    }


    // 회원 삭제
    @LoginType
    @RequestMapping(value = "/user/userDelete/admin/{username}", method = RequestMethod.GET)
    public String boardDelete(@PathVariable String username) throws Exception {

        userrepository.deleteById(username);

        return "redirect:/user/userList/admin";
    }
}
