package zerogreen.eco.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.EcoStoreUser;
import zerogreen.eco.entity.userentity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface UserRepository extends JpaRepository<User, Long> {

}
