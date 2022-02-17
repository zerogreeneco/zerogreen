package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.user.StoreMemberService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final StoreMemberService storeMemberService;

    @GetMapping("")
    public String approvedStore(Model model, UserRole userRole, HttpSession session,
                                @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (principalDetails != null && principalDetails.getBasicUser().getUserRole().equals(UserRole.USER)) {
            session.setAttribute("veganGrade", principalDetails.getVegetarianGrade());
        }
        List<NonApprovalStoreDto> result = storeMemberService.findByApprovalStore(userRole);
        log.info("yjyjyjyjyjyjyj>>>>>>>>>>" + result);
        model.addAttribute("approval", result);
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(Model model) {
        model.addAttribute("user", "USER");
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(Model model) {
        model.addAttribute("admin", "admin");
        return "admin";
    }

    @GetMapping("/store")
    public @ResponseBody String store(Model model) {
        model.addAttribute("store", "store");
        return "store";
    }

    @Secured("USER")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @GetMapping("/chat/chatIndex")
    public void chat(){
    }
    @GetMapping("/chat/chatting")
    public void chat2(){
    }
}
