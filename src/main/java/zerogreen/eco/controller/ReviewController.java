package zerogreen.eco.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import zerogreen.eco.dto.detail.ReviewDto;
import zerogreen.eco.service.detail.ReviewService;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/addReview/{id}")
    public ResponseEntity<Long> addReview(@RequestBody ReviewDto reviewDto) {
        Long rno = reviewService.saveReview(reviewDto);
        return new ResponseEntity<>(rno, HttpStatus.OK);
    }
}
