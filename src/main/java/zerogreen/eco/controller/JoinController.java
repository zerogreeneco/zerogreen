package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.MemberJoinDto;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.user.MemberService;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

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

}
