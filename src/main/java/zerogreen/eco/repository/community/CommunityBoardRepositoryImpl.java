package zerogreen.eco.repository.community;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.community.CommunityResponseDto;

import javax.persistence.EntityManager;

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
        return queryFactory
                .select(Projections.constructor(CommunityResponseDto.class,
                        communityBoard.id,
                        communityBoard.title,
                        communityBoard.text
                        ))
                .from(communityBoard, communityBoard)
                .join(member, member).on(communityBoard.member.eq(member))
                .join(boardImage, boardImage).on(boardImage.board.eq(communityBoard))
                .where(communityBoard.id.eq(boardId))
                .fetchFirst();
    }
}
