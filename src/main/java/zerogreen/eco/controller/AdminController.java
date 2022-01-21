package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.service.user.BasicUserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final BasicUserService basicUserService;

    @GetMapping("/approvalStore")
    public String approvalStore(Model model) {

        model.addAttribute("stores", basicUserService.findByNonApprovalStore());

        return "admin/approvalStore";
    }
}
