package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
import zerogreen.eco.entity.userentity.QBasicUser;
import zerogreen.eco.entity.userentity.QStoreMember;
import zerogreen.eco.entity.userentity.UserRole;

import javax.persistence.EntityManager;

import java.util.List;

import static zerogreen.eco.entity.file.QRegisterFile.*;
import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;

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
    public List<NonApprovalStoreDto> findByUnApprovalStore() {
        QBasicUser qBasicUser = basicUser;
        QStoreMember qStoreMember = qBasicUser.as(QStoreMember.class);

        return queryFactory
                .select(Projections.constructor(NonApprovalStoreDto.class,
                        qStoreMember._super.username
                        , qStoreMember.storeRegNum
                        , registerFile.id
                        , registerFile.uploadFileName
                        , qStoreMember._super.id
                ))
                .from(qStoreMember)
                .innerJoin(qStoreMember.registerFile, registerFile)
                .where(qStoreMember._super.userRole.eq(UserRole.UN_STORE))
                .fetch();
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
