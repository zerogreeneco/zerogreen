package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.entity.userentity.Member;
import zerogreen.eco.entity.userentity.VegetarianGrade;
import zerogreen.eco.service.MemberService;

@Controller
@Slf4j
@RequestMapping("/")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addForm(@ModelAttribute Member member, BindingResult result,Model model) {
        model.addAttribute("model", member);
        model.addAttribute("vegan", VegetarianGrade.values());
        return "member/register";
    }

    // 회원가입
    @PostMapping("/add")
    public String addMember(Member member) {
        log.info("member={}", member);
        memberService.save(member);
        return "redirect:add";
    }

}
