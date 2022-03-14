package zerogreen.eco.repository.list;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.file.QStoreImageFile;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.StoreType;

import javax.persistence.EntityManager;
import java.util.List;

import static zerogreen.eco.entity.userentity.QStoreMember.storeMember;
import static zerogreen.eco.entity.userentity.QStoreMenu.storeMenu;
import static zerogreen.eco.entity.userentity.UserRole.STORE;
import static zerogreen.eco.entity.detail.QLikes.likes;

public class StoreListRepositoryImpl implements StoreListRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public StoreListRepositoryImpl(EntityManager manager){this.jpaQueryFactory = new JPAQueryFactory(manager);}

    QStoreImageFile storeImage = new QStoreImageFile("storeImage");

    @Override
    public Slice<StoreDto> getShopList(Pageable pageable) {
        List<StoreDto> shopList = jpaQueryFactory
                .select(Projections.constructor(StoreDto.class,
                        storeMember.id,
                        storeMember.storeName,
                        storeMember.storeInfo.storePhoneNumber,
                        storeMember.storeInfo.openTime,
                        storeMember.storeInfo.closeTime,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeImage.thumbnailName)
                                        .from(storeImage, storeImage)
                                        .where(storeImage.id.eq(
                                                JPAExpressions
                                                        .select(storeImage.id.min())
                                                        .from(storeImage, storeImage)
                                                        .where(storeImage.storeMember.id.eq(storeMember.id)))),"listThumbnail"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeMenu.menuName)
                                        .from(storeMenu, storeMenu)
                                        .where(storeMenu.id.eq(
                                                JPAExpressions
                                                        .select(storeMenu.id.min())
                                                        .from(storeMenu, storeMenu)
                                                        .where(storeMenu.storeMember.id.eq(storeMember.id)))),"menuName"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(likes.id.count())
                                        .from(likes, likes)
                                        .where(likes.storeMember.id.eq(storeMember.id)),"likes")
                        ))
                .from(storeMember, storeMember)
                .where(storeMember._super.userRole.eq(STORE),
                        storeMember.storeType.eq(StoreType.ECO_SHOP))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<StoreMember> countQuery = jpaQueryFactory
                .selectFrom(storeMember)
                .fetch();

        return new PageImpl<>(shopList, pageable, countQuery.size());
    }

    @Override
    public Slice<StoreDto> getFoodList(Pageable pageable) {
        List<StoreDto> shopList = jpaQueryFactory
                .select(Projections.constructor(StoreDto.class,
                        storeMember.id,
                        storeMember.storeName,
                        storeMember.storeInfo.storePhoneNumber,
                        storeMember.storeInfo.openTime,
                        storeMember.storeInfo.closeTime,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeImage.thumbnailName)
                                        .from(storeImage, storeImage)
                                        .where(storeImage.id.eq(
                                                JPAExpressions
                                                        .select(storeImage.id.min())
                                                        .from(storeImage, storeImage)
                                                        .where(storeImage.storeMember.id.eq(storeMember.id)))),"listThumbnail"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeMenu.menuName)
                                        .from(storeMenu, storeMenu)
                                        .where(storeMenu.id.eq(
                                                JPAExpressions
                                                        .select(storeMenu.id.min())
                                                        .from(storeMenu, storeMenu)
                                                        .where(storeMenu.storeMember.id.eq(storeMember.id)))),"menuName"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(likes.id.count())
                                        .from(likes, likes)
                                        .where(likes.storeMember.id.eq(storeMember.id)),"likes")

                ))
                .from(storeMember, storeMember)
                .where(storeMember._super.userRole.eq(STORE),
                        storeMember.storeType.ne(StoreType.ECO_SHOP))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<StoreMember> countQuery = jpaQueryFactory
                .selectFrom(storeMember)
                .fetch();

        return new PageImpl<>(shopList, pageable, countQuery.size());
    }

    @Override
    public Slice<StoreDto> getFoodTypeList(Pageable pageable, StoreType storeType) {
        List<StoreDto> shopList = jpaQueryFactory
                .select(Projections.constructor(StoreDto.class,
                        storeMember.id,
                        storeMember.storeName,
                        storeMember.storeInfo.storePhoneNumber,
                        storeMember.storeInfo.openTime,
                        storeMember.storeInfo.closeTime,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeImage.thumbnailName)
                                        .from(storeImage, storeImage)
                                        .where(storeImage.id.eq(
                                                JPAExpressions
                                                        .select(storeImage.id.min())
                                                        .from(storeImage, storeImage)
                                                        .where(storeImage.storeMember.id.eq(storeMember.id)))),"listThumbnail"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(storeMenu.menuName)
                                        .from(storeMenu, storeMenu)
                                        .where(storeMenu.id.eq(
                                                JPAExpressions
                                                        .select(storeMenu.id.min())
                                                        .from(storeMenu, storeMenu)
                                                        .where(storeMenu.storeMember.id.eq(storeMember.id)))),"menuName"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(likes.id.count())
                                        .from(likes, likes)
                                        .where(likes.storeMember.id.eq(storeMember.id)),"likes")
                ))
                .from(storeMember, storeMember)
                .innerJoin(storeMember).on(storeMember.id.eq(storeImage.storeMember.id))
                .where(storeMember._super.userRole.eq(STORE),
                        storeMember.storeType.ne(StoreType.ECO_SHOP),
                        storeMember.storeType.eq(storeType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        List<StoreMember> countQuery = jpaQueryFactory
                .selectFrom(storeMember)
                .fetch();

        return new PageImpl<>(shopList, pageable, countQuery.size());
    }
}
