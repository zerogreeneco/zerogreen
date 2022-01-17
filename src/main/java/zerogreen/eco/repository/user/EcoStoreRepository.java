package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.EcoStoreUser;
import zerogreen.eco.entity.userentity.FoodStoreUser;

public interface EcoStoreRepository extends JpaRepository<EcoStoreUser, Long> {
}
