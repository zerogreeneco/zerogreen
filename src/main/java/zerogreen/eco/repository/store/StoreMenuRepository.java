package zerogreen.eco.repository.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import zerogreen.eco.entity.userentity.StoreMenu;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {

    @Query("select su from StoreMenu su where su.storeMember.id =:sno")
    List<StoreMenu> getMenuByStore(@Param("sno") Long sno);

    @Query("select menu from StoreMenu menu left outer join BasicUser user on menu.id = user.id")
    List<StoreMenu> updateStoreMenu(Long id);

}
