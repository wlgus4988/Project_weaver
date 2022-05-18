package com.example.test.user.mybatis.controller;

import com.example.test.aop.LoginType;
import com.example.test.user.common.securityUtils;
//import com.example.test.user.jpa.entity.Users;
//import com.example.test.user.jpa.repository.userRepository;
//import com.example.test.user.jpa.service.userService;
import com.example.test.user.mybatis.dto.UserDto;
import com.example.test.user.mybatis.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class userController {

    @Autowired
    userService userservice;

    /*@Autowired
    userRepository userrepository;*/

    private static final String LOGIN_MEMBER = "LOGIN_MEMBER";

    // ROLE_MEMBER Controller------------------------------------------------------------------------------------------------------------------//

    @LoginType
    @RequestMapping(value = "/user/userDetail/{username}", method = RequestMethod.GET)
    public String userDetail(@PathVariable String username, Model model, HttpSession session) throws Exception {

        try {
            UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");
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
            UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");
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
    public String userUpdate(@PathVariable String username, Model model, HttpSession session, UserDto user) throws Exception {

        UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");
        model.addAttribute("users", userservice.userDetail(username));

        if (user.getPw().equals("****") || securityUtils.sha256ToString(user.getPw()).equals(users.getPw())) {
            user.setPw(users.getPw());
        } else {
            user.setPw(securityUtils.sha256ToString(user.getPw()));
        }
        user.setEmail(user.getEmail());
        user.setRole(users.getRole());
        userservice.userUpdate(user);

        return "redirect:/user/userDetail/{username}";
    }

    // ROLE_ADMIN Controller------------------------------------------------------------------------------------------------------------------//

    @LoginType
    @RequestMapping(value = "/user/userList/admin", method = RequestMethod.GET)
    public String userList_admin(Model model, @PageableDefault(size = 2) Pageable pageable,
                                 @RequestParam(required = false, defaultValue = "") String searchText) throws Exception {

        List<UserDto> user = userservice.userList(searchText, searchText);

        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), user.size());
        final Page<UserDto> list = new PageImpl<>(user.subList(start, end), pageable, user.size());


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
            UserDto user = (UserDto) session.getAttribute("LOGIN_MEMBER");
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
        List<String> userList = userservice.findUsername();
        model.addAttribute("userList", userList);
        return "user/admin/userWrite_admin";
    }

    @LoginType
    @RequestMapping(value = "/user/userInsert/admin", method = RequestMethod.POST)
    public String userInsert_admin(UserDto user) throws Exception {


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
            UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");
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
    public String userAdminUpdate(@PathVariable String username, Model model, HttpSession session, UserDto user) throws Exception {

        UserDto users = (UserDto) session.getAttribute("LOGIN_MEMBER");
        model.addAttribute("users", userservice.userDetail(username));

        if (user.getPw().equals("****") || securityUtils.sha256ToString(user.getPw()).equals(userservice.userDetail(username).getPw())) {
            user.setPw(users.getPw());
        } else {
            user.setPw(securityUtils.sha256ToString(user.getPw()));
        }

        user.setEmail(user.getEmail());
        user.setRole(user.getRole());

        userservice.userUpdate(user);


        return "redirect:/user/userDetail/admin/{username}";
    }


    // 회원 삭제
    @LoginType
    @RequestMapping(value = "/user/userDelete/admin/{username}", method = RequestMethod.GET)
    public String boardDelete(@PathVariable String username) throws Exception {

        userservice.deleteById(username);

        return "redirect:/user/userList/admin";
    }
}
