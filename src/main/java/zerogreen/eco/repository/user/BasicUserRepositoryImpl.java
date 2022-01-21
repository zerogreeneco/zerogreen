package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.member.MemberAuthDto;
import zerogreen.eco.dto.store.NonApprovalStoreDto;
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
    public List<NonApprovalStoreDto> findByUnApprovalStore() {
        return queryFactory
                .select(Projections.bean(NonApprovalStoreDto.class,
                        storeMember._super.username
                        ,storeMember.storeRegNum
                ))
                .from(storeMember)
                .where(storeMember._super.userRole.eq(UserRole.UNSTORE))
                .fetch();
    }
}
