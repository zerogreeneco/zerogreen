package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.file.QRegisterFile;
import zerogreen.eco.entity.userentity.BasicUser;
import zerogreen.eco.entity.userentity.QBasicUser;
import zerogreen.eco.entity.userentity.QStoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.EntityManager;

import java.util.List;

import static zerogreen.eco.entity.file.QRegisterFile.*;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;
import static zerogreen.eco.entity.userentity.QStoreMember.*;

public class BasicUserRepositoryImpl implements BasicUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasicUserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

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

    @Override
    public Page<NonApprovalStoreDto> findByUnApprovalStore(Pageable pageable) {
        QBasicUser qBasicUser = basicUser;
        QStoreMember qStoreMember = qBasicUser.as(QStoreMember.class);

        List<NonApprovalStoreDto> content = queryFactory
                .select(Projections.constructor(NonApprovalStoreDto.class,
                        qStoreMember._super.username
                        , qStoreMember._super.id
                        , qStoreMember.storeRegNum
                        , registerFile.id
                        , registerFile.uploadFileName
                ))
                .from(qStoreMember)
                .innerJoin(qStoreMember.registerFile, registerFile)
                .where(qStoreMember._super.userRole.eq(UserRole.UN_STORE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<BasicUser> countQuery = queryFactory
                .selectFrom(basicUser)
                .where(basicUser.userRole.eq(UserRole.UN_STORE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    @Override
    public void changeUserRole(List<Long> memberId) {
        queryFactory
                .update(basicUser)
                .set(basicUser.userRole, UserRole.STORE)
                .where(basicUser.id.in(memberId))
                .execute();


    }
}
