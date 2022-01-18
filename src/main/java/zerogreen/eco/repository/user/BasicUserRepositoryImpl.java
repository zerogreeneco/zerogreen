package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.MemberAuthDto;

import javax.persistence.EntityManager;

import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;

public class BasicUserRepositoryImpl implements BasicUserRepositoyCustom {

    private final JPAQueryFactory queryFactory;

    public BasicUserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberAuthDto findAuthMember(Long id) {
        return queryFactory
                .select(Projections.bean(MemberAuthDto.class,
                        basicUser.nickname,
                        basicUser.authKey))
                .from(basicUser)
                .where(basicUser.id.eq(id))
                .fetchFirst();
    }
}
