package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.LikesService;
import zerogreen.eco.service.user.StoreMemberService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class PageController {

    private final StoreMemberService storeMemberService;
    private final LikesService likesService;

    @GetMapping("/page/detail")
    public void detail(Long id, Model model, String username, LikesDto likesDto,
                       @PrincipalUser BasicUser basicUser){
        //스토어 데이터 + 회원/비회원
        StoreDto storeDto = storeMemberService.getStore(id);
        if (basicUser == null) {
            model.addAttribute("getStore",storeDto);
        } else {
            model.addAttribute("getStore",storeDto);
            model.addAttribute("member", basicUser.getUsername());
        }

        //라이크 카운팅
        Long cnt = likesService.cntLikes(likesDto);
        if (cnt != null) {
            model.addAttribute("cnt",cnt);
        }


    }

    @GetMapping("/member/memberMyInfo")
    public void connect2(){

    }

}