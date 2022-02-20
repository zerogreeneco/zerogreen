package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.user.MemberRepository;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.community.BoardImageService;
import zerogreen.eco.service.community.CommunityBoardService;
import zerogreen.eco.service.community.CommunityReplyService;
import zerogreen.eco.service.file.FileService;
import zerogreen.eco.service.user.MemberService;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final StoreMemberService storeMemberService;
    private final MemberRepository memberRepository;
    private final CommunityBoardService boardService;

    @GetMapping("")
    public String approvedStore(Model model, UserRole userRole, HttpSession session,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null && principalDetails.getBasicUser().getUserRole().equals(UserRole.USER)) {
            session.setAttribute("veganGrade", principalDetails.getVegetarianGrade());
        }
        List<NonApprovalStoreDto> result = storeMemberService.findByApprovalStore(userRole);
        model.addAttribute("approval", result);
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody
    String user(Model model) {
        model.addAttribute("user", "USER");
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody
    String admin(Model model) {
        model.addAttribute("admin", "admin");
        return "admin";
    }

    @GetMapping("/store")
    public @ResponseBody
    String store(Model model) {
        model.addAttribute("store", "store");
        return "store";
    }

    @Secured("USER")
    @GetMapping("/info")
    public @ResponseBody
    String info() {
        return "개인정보";
    }

//    @GetMapping("/chatting/{memberId}")
//    public String detailView(@PathVariable("memberId") Long memberId, Model model) {
//
//        model.addAttribute("chat", boardService.findId(memberId));
//        log.info(boardService.findId(memberId)+"detailViewyjyjyjyjyjyjyjyjyj");
//        return "/chat/chatIndex";
//    }

    @GetMapping("/chatting/{boardId}")
    public String detailView(@PathVariable("boardId") Long boardId, Model model) {
        model.addAttribute("user", memberRepository.findmemberId(boardId));
        log.info(memberRepository.findmemberId(boardId)+"er1111111");
        model.addAttribute("nick", memberRepository.findmemberNick(boardId));
        log.info(memberRepository.findmemberNick(boardId)+"er2222222");
        return "chat/chatIndex";
    }

    @GetMapping("/chatting/chatIndex")
    public void chat2() {
    }
}
