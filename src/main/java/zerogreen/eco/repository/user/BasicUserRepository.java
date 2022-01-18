package zerogreen.eco.repository.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.BasicUser;

import javax.persistence.EntityManager;

public interface BasicUserRepository extends JpaRepository<BasicUser, Long>, BasicUserRepositoyCustom {

    public BasicUser findByUsername(String username);


}
