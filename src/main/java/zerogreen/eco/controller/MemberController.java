package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final BasicUserService basicUserService;
    private final MemberService memberService;
    private final MailService mailService;

    @GetMapping("/findMember")
    public String findMemberForm(@ModelAttribute("findMember") FindMemberDto findMemberForm) {

        return "member/findMember";
    }

    /*
    * 이메일 찾기
    * */
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

    /*
    * 비밀번호 찾기
    * */
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

        // 결과가 없을 때 메시지 설정 필요
        return "member/findMember";
    }

    @GetMapping("/account")
    public String memberInfoForm(@AuthenticationPrincipal PrincipalDetails principalDetails, MemberUpdateDto memberUpdateDto, Model model) {



//        log.info("MEMBERID={}", memberId);
        MemberUpdateDto updateDto = memberService.toMemberUpadteDto(principalDetails.getUsername(), memberUpdateDto);

        log.info("UPDATEDTO={}", updateDto);

        model.addAttribute("member", updateDto);

        return "member/updateMember";
    }
}
