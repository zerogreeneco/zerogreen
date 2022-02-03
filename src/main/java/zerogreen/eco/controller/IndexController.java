package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.service.user.StoreMemberService;

import java.util.List;

@Controller
@Slf4j
@RequestMapping
@RequiredArgsConstructor
public class IndexController {

    private final StoreMemberService storeMemberService;

    @GetMapping("")
    public String approvedStore(Model model, UserRole userRole) {
        List<StoreMember> result = storeMemberService.findByApprovedStore(userRole);
        log.info("storeDTO>>>>>>>>>>"+result);
        log.info("USERROLE={}", userRole);
        model.addAttribute("approved", result);
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

}
