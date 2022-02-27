package zerogreen.eco.repository.detail;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.detail.DetailReviewDto;
import zerogreen.eco.entity.file.QStoreImageFile;

import javax.persistence.EntityManager;
import java.util.List;

import static zerogreen.eco.entity.detail.QDetailReview.detailReview;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QStoreMember.storeMember;

public class DetailReviewRepositoryImpl implements DetailReviewRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public DetailReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    QStoreImageFile subImage = new QStoreImageFile("subImage");

    //회원별 리뷰 남긴 가게 리스트(memberMyInfo)
    @Override
    public List<DetailReviewDto> getReviewByUser(Long id) {
        List<DetailReviewDto> content = queryFactory
                .select(Projections.constructor(DetailReviewDto.class,
                        detailReview.id,
                        detailReview.reviewText,
                        storeMember.id,
                        storeMember.storeName,
                        basicUser.id,
                        detailReview.createdDate,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(subImage.thumbnailName)
                                        .from(subImage, subImage)
                                        .where(subImage.id.eq(
                                                JPAExpressions
                                                        .select(subImage.id.min())
                                                        .from(subImage, subImage)
                                                        .where(subImage.storeMember.id.eq(storeMember.id)))), "thumbImage")

                        ))
                .from(detailReview, detailReview)
                .leftJoin(detailReview.storeMember, storeMember).on(storeMember.id.eq(detailReview.storeMember.id))
                .leftJoin(detailReview.reviewer, basicUser).on(basicUser.id.eq(detailReview.reviewer.id))
                .where(detailReview.reviewer.id.eq(id))
                .fetch();
        return content;
    }

}
