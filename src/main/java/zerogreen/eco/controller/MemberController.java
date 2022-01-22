package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.BasicUserService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final BasicUserService basicUserService;
    private final MailService mailService;

    @GetMapping("/findMember")
    public String findMemberForm(@ModelAttribute("findMember") FindMemberDto findMemberForm) {

        return "member/findMember";
    }

/*    @PostMapping("/findMember")
    public String findId(@Validated @ModelAttribute("findMember") FindMemberDto findMemberDto,
                         BindingResult bindingResult, Model model) {

        String phoneNumber = findMemberDto.getPhoneNumber();

        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError allError : allErrors) {
                log.info("ERROR={}", allError);
            }
        }
        long count = basicUserService.countByPhoneNumber(phoneNumber);
        model.addAttribute("count", count);

        if (count == 1) {
            FindMemberDto findUsername = basicUserService.findByPhoneNumber(phoneNumber);
            model.addAttribute("username", findUsername.getUsername());

            log.info("findMember={}", findUsername.getUsername());
            return "member/findMember";
        } else {
            model.addAttribute("username1", "일치하는 아이디가 없습니다. ");
        }
        return "member/findMember";
    }*/

    @PostMapping("/findMember")
    public String findPassword(@Validated @ModelAttribute("findMember") FindMemberDto findMemberDto,
                               BindingResult bindingResult, Model model) {
        String username = findMemberDto.getUsername();
        String phoneNum = findMemberDto.getPhoneNumber();

        log.info("USERNAME={}", username);
        log.info("PHONENUM={}", phoneNum);

        long count = basicUserService.countByUsername(username);

        if (count == 1) {
            mailService.sendTempPassword(username, phoneNum);
        }
        return "member/findMember";
    }
}
