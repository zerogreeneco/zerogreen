package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.detail.LikesDto;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.security.auth.PrincipalUser;
import zerogreen.eco.service.detail.LikesService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/addLikes/{id}")
    public ResponseEntity<Long> addLikes(@RequestBody Likes likes,
                                         @PrincipalUser BasicUser basicUser) {
        Long likesNum = likesService.addLikes(likes);
        return new ResponseEntity<>(likesNum, HttpStatus.OK);

    }
}