package com.example.test.login.controller;

import com.example.test.aop.LoginType;
import com.example.test.user.common.securityUtils;
import com.example.test.user.jpa.entity.Users;
import com.example.test.user.jpa.repository.userRepository;
import com.example.test.user.jpa.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

//@Controller
public class loginController_jpa {

    @Autowired
    userService userservice;

    @Autowired
    userRepository userrepository;

    private static final String LOGIN_MEMBER = "LOGIN_MEMBER";

    @RequestMapping("/")
    public String Home() throws Exception {

        return "login/signIn";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String Login(HttpSession session, @RequestParam String username, @RequestParam String password, Model model) throws Exception {

        try {
            Optional<Users> optional = userrepository.findById(username);  // findeById 는 Optional 형식을 반환
            // 아이디가 없을 수도 있기 때문에 null 체크가 필요없는 optional을 쓴다
            if (optional.isPresent()) {
                Users user = optional.get();
                System.out.println("회원 있음");
                String pw = securityUtils.sha256ToString(password);

                if (user.getPw().equals(pw)) {
                    System.out.println("로그인 성공");

                    session.setAttribute(LOGIN_MEMBER, user);
                    System.out.println(session.getAttribute("LOGIN_MEMBER"));

                    return "redirect:/home";

                } else {
                    model.addAttribute("msg", "비밀번호가 틀렸습니다");  // 실패
                    System.out.println("로그인 실패");
                    return "login/signIn";
                }
            } else {
                model.addAttribute("msg", "존재하지 않는 회원입니다");
                System.out.println("회원 없음");
                return "login/signUp";
            }
        }
        catch (NullPointerException e){
            return "login/signIn";
        }
    }


    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String Join(Model model) throws Exception {
        List<String> userList = userrepository.findUsername();
        model.addAttribute("userList", userList);
        return "login/signUp";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public String signUp(Users user) throws Exception {

        String pw = securityUtils.sha256ToString(user.getPw());
        System.out.println(pw);
        user.setPw(pw);
        user.setRole("ROLE_MEMBER");
        userservice.userSave(user);

        return "redirect:/";
    }

    @LoginType
    @RequestMapping("/home")
    public String loginSuccess() throws Exception {

        return "index";
    }


    @GetMapping("/login/logout")
    public String logout(HttpSession session) throws Exception {

        session.invalidate();
        System.out.println(session);

        return "redirect:/";
    }
}
