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

import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QStoreMember.storeMember;
import static zerogreen.eco.entity.userentity.UserRole.STORE;

public class StoreListRepositoryImpl implements StoreListRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public StoreListRepositoryImpl(EntityManager manager){
        this.jpaQueryFactory = new JPAQueryFactory(manager);
    }

    private final QBasicUser BasicUser = basicUser;
    private final QStoreMember StoreMember = BasicUser.as(QStoreMember.class);


    @Override
    public Slice<StoreDto> getShopList(Pageable pageable) {
        List<StoreDto> shopList = jpaQueryFactory
                .select(Projections.constructor(StoreDto.class,
                        StoreMember.id,
                        StoreMember.storeName,
                        storeMember.storeInfo.storePhoneNumber,
                        storeMember.storeInfo.openTime,
                        storeMember.storeInfo.closeTime
                ))
                .from(StoreMember)
                .where(StoreMember._super.userRole.eq(STORE),
                        storeMember.storeType.eq(StoreType.ECO_SHOP))
                .fetch();

        List<StoreMember> countQuery = jpaQueryFactory
                .selectFrom(storeMember)
                .fetch();


        return new PageImpl<>(shopList, pageable, countQuery.size());
    }
}
