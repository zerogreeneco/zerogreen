package zerogreen.eco.repository.community;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.community.CommunityResponseDto;
import zerogreen.eco.entity.community.Category;
import zerogreen.eco.entity.community.CommunityBoard;

import javax.persistence.EntityManager;

import java.util.List;

import static zerogreen.eco.entity.community.QBoardImage.boardImage;
import static zerogreen.eco.entity.community.QCommunityBoard.communityBoard;
import static zerogreen.eco.entity.userentity.QMember.member;

public class CommunityBoardRepositoryImpl implements CommunityBoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public CommunityBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CommunityResponseDto findDetailBoard(Long boardId) {
        return null;
    }

    /*
    * 커뮤니티 게시판 전체 리스트
    * */
    @Override
    public Slice<CommunityResponseDto> findAllCommunityList(Pageable pageable) {
        List<CommunityResponseDto> content = queryFactory
                .select(Projections.constructor(CommunityResponseDto.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.text,
                        member.nickname,
                        communityBoard.category,
                        communityBoard.modifiedDate,
                        communityBoard.count
                ))
                .from(communityBoard, communityBoard)
                .join(communityBoard.member, member)
                .orderBy(communityBoard.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CommunityBoard> countQuery = queryFactory
                .selectFrom(communityBoard)
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    };

    /*
    * 카테고리별 리스트 출력
    * */
    @Override
    public Slice<CommunityResponseDto> findByCategory(Pageable pageable, Category category) {
        List<CommunityResponseDto> content = queryFactory
                .select(Projections.constructor(CommunityResponseDto.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.text,
                        member.nickname,
                        communityBoard.category,
                        communityBoard.modifiedDate,
                        communityBoard.count
                ))
                .from(communityBoard, communityBoard)
                .join(communityBoard.member, member)
                .where(communityBoard.category.eq(category))
                .orderBy(communityBoard.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CommunityBoard> countQuery = queryFactory
                .selectFrom(communityBoard)
                .where(communityBoard.category.eq(category))
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    }

    /*
    * 상세 보기
    * */
    @Override
    public CommunityResponseDto findDetailView(Long id) {

        queryFactory
                .update(communityBoard)
                .set(communityBoard.count, communityBoard.count.add(1))
                .where(communityBoard.id.eq(id))
                .execute();

        return queryFactory
                .select(Projections.constructor(CommunityResponseDto.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.text,
                        member.nickname,
                        communityBoard.category,
                        communityBoard.modifiedDate,
                        communityBoard.count
                ))
                .from(communityBoard, communityBoard)
                .join(communityBoard.member, member)
                .where(communityBoard.id.eq(id))
                .fetchFirst();
    }
}
