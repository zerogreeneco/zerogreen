package zerogreen.eco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import zerogreen.eco.entity.userentity.BasicUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginForm(String error, Model model, HttpServletRequest request, HttpSession session) {

        String referer = request.getHeader("Referer");
        request.getSession().setAttribute("redirectURI", referer);

        if (error != null) {
            model.addAttribute("error", "아이디 비번 체크");
        }

        return "login/loginForm";
    }

/*    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }*/
}
