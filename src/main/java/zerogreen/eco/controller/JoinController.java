package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/*    @PostMapping("/checkMail")
    @ResponseBody
    public String sendMail(String mail) {


        log.info("이메일 인증 컨트롤러 OK");
        log.info("EMAIL ={}", mail);

        String key = mailService.sendAuthMail(mail);
        log.info("keyMap={}", key);
        return key;
    }*/

    @GetMapping("/authCheck")
    public String authCheckForm() {

        return "register/authConfirm";
    }

    @PostMapping("/authCheck")
    public String authForm(@RequestParam("memberId") Long id, @RequestParam("inputKey") String inputKey,
                           RedirectAttributes redirectAttributes) {

        log.info("JOIN MEMEBER ID >>> " + id);
        log.info("INPUTKEY >>> " + inputKey);

        Member findMember = memberService.findById(id).orElseGet(null);

        MemberAuthDto authMember = memberService.findAuthMember(findMember.getId());
        log.info("DTO ={}", authMember.getNickname());

        if (findMember.getAuthKey().equals(inputKey)) {
            memberService.changeAuthState(id);
            redirectAttributes.addAttribute("nickname", authMember.getNickname());
            return "redirect:/members/welcome";
        }

        return "register/authConfirm";
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam("nickname") String nickname, Model model) {

        model.addAttribute("nickname", nickname);

        return "register/welcome";
    }
}
