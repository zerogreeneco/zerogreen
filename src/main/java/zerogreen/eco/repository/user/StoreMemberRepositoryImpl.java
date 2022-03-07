package zerogreen.eco.repository.user;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.dto.store.StoreMenuDto;
import zerogreen.eco.entity.community.QCommunityLike;
import zerogreen.eco.entity.detail.QDetailReview;
import zerogreen.eco.entity.detail.QLikes;
import zerogreen.eco.entity.file.QStoreImageFile;
import zerogreen.eco.entity.file.StoreImageFile;
import zerogreen.eco.entity.userentity.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.ExpressionUtils.*;
import static zerogreen.eco.entity.file.QStoreImageFile.storeImageFile;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QStoreMember.storeMember;
import static zerogreen.eco.entity.userentity.QStoreMenu.storeMenu;

public class StoreMemberRepositoryImpl implements StoreMemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public StoreMemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private final QBasicUser asBasicUser = basicUser;
    private final QStoreMember asStoreMember = asBasicUser.as(QStoreMember.class);

    @Override
    public List<NonApprovalStoreDto> findByApprovalStore(UserRole userRole) {
        List<NonApprovalStoreDto> content = queryFactory
                .select(Projections.constructor(NonApprovalStoreDto.class,
                        asStoreMember.id
                        ,asStoreMember.storeName
                        ,asStoreMember.storeInfo.storeAddress
                        ,asStoreMember.storeType
                ))
                .from(asStoreMember)
                .where(asStoreMember._super.userRole.eq(userRole.STORE))
                .fetch();
        return content;
    }

    //Store DB (Detail)
    @Override
    public StoreDto getStoreById(Long sno) {
        QLikes subLike = new QLikes("subLike");
        QDetailReview subReview = new QDetailReview("subReview");
            return queryFactory
                .select(Projections.constructor(StoreDto.class,
                        storeMember.id,
                        storeMember.storeName,
                        storeMember.storeType,
                        storeMember.storeInfo,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(count(subLike.id))
                                        .from(subLike, subLike)
                                        .where(subLike.storeMember.id.eq(sno)),"likesCount"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(count(subReview.id))
                                        .from(subReview, subReview)
                                        .where(subReview.storeMember.id.eq(sno)),"reviewCnt")
                        ))
                .from(storeMember, storeMember)
                .where(storeMember.id.eq(sno))
                .fetchFirst();
    }

}
