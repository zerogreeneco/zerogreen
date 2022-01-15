package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.MemberJoinDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.user.MemberService;

import java.util.Random;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final JavaMailSender javaMailSender;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") MemberJoinDto member, Model model) {
        model.addAttribute("vegan", VegetarianGrade.values());
        return "register/registerForm";
    }

    @PostMapping("/add")
    public String addMember(@Validated @ModelAttribute("member") MemberJoinDto member, BindingResult bindingResult, Model model) {
        log.info("member={}", member);

        if (bindingResult.hasErrors()) {
            model.addAttribute("vegan", VegetarianGrade.values());
            return "register/registerForm";
        }

        Member joinMember = member.toMember(member);
        memberService.save(joinMember);
        log.info("joinMember={}",joinMember);

        return "redirect:/login";
    }

    @PostMapping("/checkMail")
    @ResponseBody
    public String sendMail(String mail) {
        log.info("이메일 인증 컨트롤러 OK");
        log.info("EMAIL ={}",mail);

        Random random = new Random(); // 난수 생성
        String key = ""; // 인증 번호

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail); // View에서 보낸 가입 이메일 주소
        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(25) + 65; // A~Z 랜덤 알파벳
            key += (char) index;
        }
        int numIndex = random.nextInt(9999) + 1000; // 4자리 랜덤 정수
        key += numIndex;

        message.setSubject("ZEROGREEN 회원 가입을 위한 인증번호 메일");
        message.setText("인증번호 : " + key);

        javaMailSender.send(message);
        log.info("key={}", key);
        return key;
    }

}
