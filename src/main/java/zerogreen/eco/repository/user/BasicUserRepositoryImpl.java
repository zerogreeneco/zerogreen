package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.QBasicUser;
import zerogreen.eco.entity.userentity.QStoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.EntityManager;
import java.util.List;

import static zerogreen.eco.entity.file.QRegisterFile.registerFile;
import static zerogreen.eco.entity.userentity.QBasicUser.*;

public class BasicUserRepositoryImpl implements BasicUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasicUserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private final QBasicUser asBasicUser = basicUser;
    private final QStoreMember asStoreMember = asBasicUser.as(QStoreMember.class);

    @Override
    public MemberAuthDto findByAuthUsername(String username) {
        return queryFactory
                .select(Projections.bean(MemberAuthDto.class,
                        basicUser.username
                ))
                .from(basicUser)
                .where(basicUser.username.eq(username))
                .fetchFirst();
    }

    /*
    * 비승인 가게 회원 조회 (승인용)
    * */
    @Override
    public Page<NonApprovalStoreDto> findByUnApprovalStore(Pageable pageable) {

        List<NonApprovalStoreDto> content = queryFactory
                .select(Projections.constructor(NonApprovalStoreDto.class,
                        asStoreMember._super.id
                        , asStoreMember._super.username
                        , asStoreMember.storeName
                        , asStoreMember.storeInfo.storePhoneNumber
                        , asStoreMember.storeInfo.storeAddress
                        , asStoreMember.storeRegNum
                        , registerFile.id
                        , registerFile.uploadFileName
                ))
                .from(asStoreMember)
                .innerJoin(asStoreMember.registerFile, registerFile)
                .where(asStoreMember._super.userRole.eq(UserRole.UN_STORE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BasicUser> countQuery = queryFactory
                .selectFrom(basicUser)
                .where(basicUser.userRole.eq(UserRole.UN_STORE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    /*
    * 비승인 가게 회원 USER_ROLE 변경 (승인용)
    * */
    @Override
    public void changeUserRole(List<Long> memberId) {
        queryFactory
                .update(basicUser)
                .set(basicUser.userRole, UserRole.STORE)
                .where(basicUser.id.in(memberId))
                .execute();


    }

    /*
    * 검색 조건 및 페이징
    * */
    @Override
    public Page<NonApprovalStoreDto> searchAndPaging(NonApprovalStoreDto condition, Pageable pageable) {
        List<NonApprovalStoreDto> content = queryFactory
                .select(Projections.constructor(NonApprovalStoreDto.class
                        , asStoreMember.storeName
                        , asStoreMember.storeInfo.storePhoneNumber
                        , asStoreMember.storeInfo.storeAddress
                ))
                .from(asStoreMember)
                .innerJoin(asStoreMember.registerFile, registerFile)
                .where(
                        usernameContains(condition.getUsername()),
                        storeNameContains(condition.getStoreName()),
                        storeRegNumContains(condition.getStoreRegNum())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BasicUser> countQuery = queryFactory
                .selectFrom(basicUser)
                .where(basicUser.userRole.eq(UserRole.UN_STORE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression storeRegNumContains(String storeRegNum) {
        return StringUtils.hasText(storeRegNum) ? asStoreMember.storeRegNum.contains(storeRegNum) : null;
    }

    private BooleanExpression storeNameContains(String storeName) {
        return StringUtils.hasText(storeName) ? asStoreMember.storeName.contains(storeName) : null;
    }

    private BooleanExpression usernameContains(String username) {
        return StringUtils.hasText(username) ? asBasicUser.username.contains(username) : null;
    }
}
