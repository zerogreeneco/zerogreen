package zerogreen.eco.repository.community;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.community.CommunityResponseDto;
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

    @Override
    public Slice<CommunityResponseDto> findAllCommunityList(Pageable pageable) {
        List<CommunityResponseDto> content = queryFactory
                .select(Projections.constructor(CommunityResponseDto.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.text,
                        member.nickname,
                        communityBoard.category
                ))
                .from(communityBoard, communityBoard)
                .leftJoin(communityBoard.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<CommunityBoard> countQuery = queryFactory
                .selectFrom(communityBoard)
                .fetch();

        return new PageImpl<>(content, pageable, countQuery.size());
    };
}
