package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zerogreen.eco.dto.MemberAuthDto;
import zerogreen.eco.dto.MemberJoinDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberJoinDto member, Model model) {

        model.addAttribute("vegan", VegetarianGrade.values());
        return "register/registerForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberJoinDto member, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        log.info("member={}", member);

        if (bindingResult.hasErrors()) {
            model.addAttribute("vegan", VegetarianGrade.values());
            return "register/registerForm";
        }

        Member joinMember = member.toMember(member);
        Long saveMember = memberService.saveV2(joinMember);
        log.info("joinMember={}", joinMember);

        redirectAttributes.addAttribute("memberId", saveMember);
//        return "redirect:/login";
        return "redirect:/members/authCheck";
    }

    /*
    * 이메일 인증
    * */
    @PostMapping("/checkMail")
    @ResponseBody
    public HashMap<String, String> sendMail(String mail) {

        HashMap<String, String> keyMap = new HashMap<>();

        log.info("이메일 인증 컨트롤러 OK");
        log.info("EMAIL ={}", mail);
        String key = mailService.createAuthKey();

        keyMap.put("key", key);
        log.info("Before Send Key={}", key);
        mailService.sendAuthMail(mail, key);
        log.info("key={}", key);

        return keyMap;
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam("nickname") String nickname, Model model) {

        model.addAttribute("nickname", nickname);

        return "register/welcome";
    }
}
