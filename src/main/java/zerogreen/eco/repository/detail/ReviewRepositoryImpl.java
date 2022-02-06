package zerogreen.eco.repository.detail;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import zerogreen.eco.dto.detail.MemberReviewDto;
import zerogreen.eco.entity.detail.MemberReview;
import zerogreen.eco.entity.detail.QStoreReview;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static zerogreen.eco.entity.detail.QMemberReview.memberReview;
import static zerogreen.eco.entity.detail.QStoreReview.storeReview;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QMember.member;

public class ReviewRepositoryImpl implements ReviewRepository{

    private final JPAQueryFactory queryFactory;
    public ReviewRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    //멤버리뷰+사업자리뷰 페이징 ** 작업중 **
    @Override
    public Page<MemberReviewDto> findByStore(Pageable pageable, StoreMember storeMember) {
        List<MemberReviewDto> content = queryFactory
                .select(Projections.constructor(MemberReviewDto.class,
                        memberReview.id,
                        memberReview.reviewText,
                        memberReview.basicUser,
                        memberReview.storeMember,
                        member.nickname,
                        storeReview,
                        memberReview.createdDate,
                        memberReview.modifiedDate


                ))
                .from(memberReview, memberReview)
                .leftJoin(member)
                .on(memberReview.basicUser.eq(member._super))
                .leftJoin(storeReview)
                .on(memberReview.id.eq(storeReview.memberReview.id))
                .where(memberReview.storeMember.eq(storeMember))
                .orderBy(memberReview.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<MemberReview> countQuery = queryFactory
                .selectFrom(memberReview)
                .where(memberReview.storeMember.eq(storeMember))
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    }

}
