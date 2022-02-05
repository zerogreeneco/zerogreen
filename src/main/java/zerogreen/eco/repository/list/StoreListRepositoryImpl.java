package zerogreen.eco.repository.list;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.QBasicUser;
import zerogreen.eco.entity.userentity.QStoreMember;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreType;

import javax.persistence.EntityManager;
import java.awt.print.Pageable;
import java.util.List;

import static zerogreen.eco.entity.community.QCommunityBoard.communityBoard;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QMember.member;
import static zerogreen.eco.entity.userentity.QStoreMember.storeMember;
import static zerogreen.eco.entity.userentity.UserRole.STORE;

public class StoreListRepositoryImpl implements StoreListRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public StoreListRepositoryImpl(EntityManager manager){
        this.jpaQueryFactory = new JPAQueryFactory(manager);
    }

//    private final QBasicUser BasicUser =new QBasicUser(basicUser);
//    private final QStoreMember StoreMember = new QStoreMember(storeMember);

    @Override
    public Slice<StoreDto> getShopList(Pageable pageable) {
        List<StoreDto> shopList = jpaQueryFactory
                .select(Projections.constructor(StoreDto.class,
                        storeMember.id,
                        storeMember.storeName,
                        storeMember.storeInfo.storePhoneNumber,
                        storeMember.storeInfo.openTime,
                        storeMember.storeInfo.closeTime
                ))
                .from(storeMember, storeMember)
                .join(storeMember, member)
                .where(member.userRole.eq(STORE),
                        storeMember.storeType.eq(StoreType.ECO_SHOP))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<StoreMember> countQuery = jpaQueryFactory
                .selectFrom(storeMember)
                .fetch();


        return new PageImpl<>(shopList, pageable, countQuery.size());
    }
}
