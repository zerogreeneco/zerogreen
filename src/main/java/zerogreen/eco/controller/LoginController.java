package zerogreen.eco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import zerogreen.eco.entity.userentity.BasicUser;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("member") BasicUser basicUser) {
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
