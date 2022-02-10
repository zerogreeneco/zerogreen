package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.dto.member.FindMemberDto;
import zerogreen.eco.dto.member.MemberUpdateDto;
import zerogreen.eco.dto.member.PasswordUpdateDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.detail.ReviewService;
import zerogreen.eco.service.mail.MailService;
import zerogreen.eco.service.user.BasicUserService;
import zerogreen.eco.service.user.MemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final BasicUserService basicUserService;
    private final MemberService memberService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;
    private final LikesService likesService;

    @ModelAttribute("member")
    public MemberUpdateDto memberUpdateDto() {
        return new MemberUpdateDto();
    }

    @ModelAttribute("password")
    private PasswordUpdateDto passwordUpdateDto() {
        return new PasswordUpdateDto();
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

    @PostMapping("/acccount/deleteMember")
    @ResponseBody
    public String deleteMember(@Validated @ModelAttribute("password") PasswordUpdateDto passwordUpdateDto,
                               BindingResult bindingResult, @AuthenticationPrincipal PrincipalDetails principalDetails, HttpSession session) {

        if (passwordEncoder.matches(passwordUpdateDto.getPassword(), principalDetails.getPassword())) {
            basicUserService.memberDelete(principalDetails.getId());
            session.invalidate();
        }
            return "redirect:/";
    }

    @GetMapping("/member/memberMyInfo")
    public String myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails,
                         Model model) {

        //회원별 남긴 리뷰 수
        model.addAttribute("reviewCount", reviewService.countReviewByUser(principalDetails.getId()));
        //회원별 좋아요 수
        model.addAttribute("likesCount", likesService.countLikesByUser(principalDetails.getId()));
        //회원 닉네임
        model.addAttribute("profile", memberService.findById(principalDetails.getId()).get());
        //회원별 리뷰 남긴 가게 리스트
        List<MemberReviewDto> review =  reviewService.getReviewByUser(principalDetails.getId());
        //review.sort(Collections.reverseOrder());
        model.addAttribute("listOfReview",review);
        //회원별 찜한 가게 리스트 (memberMyInfo)
        List<LikesDto> likes =  likesService.getLikesByUser(principalDetails.getId());
        model.addAttribute("listOfLikes",likes);


        return "member/memberMyInfo";
    }


}
