package zerogreen.eco.repository.detail;

import zerogreen.eco.dto.detail.DetailReviewDto;

import java.util.List;

public interface DetailReviewRepositoryCustom {

    //회원별 리뷰남긴 가게 리스트 (memberMyInfo)
    List<DetailReviewDto> getReviewByUser(Long id);

    }
