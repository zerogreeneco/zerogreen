package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PageController {

    private final StoreMemberService storeMemberService;

    @GetMapping("/page/detail")
    public void detail(Long id, Model model){
        StoreDto storeDto = storeMemberService.getStore(id);
        model.addAttribute("getStoreTemp",storeDto);
    }

    @GetMapping("/member/memberMyInfo")
    public void connect2(){

    }

}
