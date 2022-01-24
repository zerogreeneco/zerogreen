package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final BasicUserService basicUserService;
    private final MemberService memberService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    @ModelAttribute("member")
    public MemberUpdateDto memberUpdateDto() {
        return new MemberUpdateDto();
    }

    @ModelAttribute("password")
    private PasswordUpdateDto passwordUpdateDto() {
        return new PasswordUpdateDto();
    }

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
    public String memberInfoForm(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 MemberUpdateDto memberUpdateDto, PasswordUpdateDto passwordUpdateDto, Model model) {

//        log.info("MEMBERID={}", memberId);
        MemberUpdateDto updateDto = memberService.toMemberUpdateDto(principalDetails.getUsername(), memberUpdateDto);
        log.info("UPDATEDTO={}", updateDto);

        model.addAttribute("member", updateDto);
        model.addAttribute("password", passwordUpdateDto);

        return "member/updateMember";
    }

    /*
    * 회원 정보 수정
    * */
    @PostMapping("/account")
    @ResponseBody
    public String memberInfoUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                    @Validated @ModelAttribute("member") MemberUpdateDto memberUpdateResponse, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/updateMember";
        }

        memberService.memberUpdate(principalDetails.getId(), memberUpdateResponse);
        log.info("회원 정보 수정 성공!!!!!");

        return "member/updateMember";
    }

    /*
    * 비밀번호 변경
    * */
    @PostMapping("/account/pwdChange")
    @ResponseBody
    public String passwordChange(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                 @Validated @ModelAttribute("password") PasswordUpdateDto passwordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/updateMember";
        }

        log.info("password={}", passwordDto.getPassword());
        log.info("newpassword={}", passwordDto.getNewPassword());

        if (passwordEncoder.matches(passwordDto.getPassword(), principalDetails.getPassword())) {
            basicUserService.passwordChange(principalDetails.getId(), passwordDto);
            log.info("비밀번호 변경 성공!!!!!");
        } else {
            log.info("비밀번호 변경 실패....ㅠㅠ");
        }
        return "member/updateMember";
    }

}
