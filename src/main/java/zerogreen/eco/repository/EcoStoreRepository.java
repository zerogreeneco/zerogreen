package zerogreen.eco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.userentity.FoodStoreUser;

public interface EcoStoreRepository extends JpaRepository<FoodStoreUser, Long> {
}
