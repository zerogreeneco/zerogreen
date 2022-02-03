package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.security.auth.PrincipalDetails;
import zerogreen.eco.service.detail.LikesService;

import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class LikesController {

    private final LikesService likesService;

    //Add Likes
    @ResponseBody
    @PostMapping("/addLikes/{id}")
    public Long addLikes(@RequestBody LikesDto likesDto,
                         @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long result = likesService.addLikes(likesDto);
        return result;
    }

    //delete Likes
    @ResponseBody
    @DeleteMapping("/deleteLikes/{sno}/{id}")
    public HashMap<String, String> deleteLikes(@PathVariable Long id) {
        HashMap<String, String> key = new HashMap<>();
        likesService.remove(id);
        return key;
    }
}
