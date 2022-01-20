package zerogreen.eco.repository.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import zerogreen.eco.dto.MemberAuthDto;

import javax.persistence.EntityManager;

import static zerogreen.eco.entity.userentity.QBasicUser.basicUser;

public class BasicUserRepositoryImpl implements BasicUserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BasicUserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberAuthDto findByAuthUsername(String username) {
        return queryFactory
                .select(Projections.fields(MemberAuthDto.class,
                        basicUser.username))
                .from(basicUser)
                .where(basicUser.username.eq(username))
                .fetchFirst();
    }
}
