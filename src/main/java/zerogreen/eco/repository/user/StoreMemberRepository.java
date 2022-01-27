package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;
import java.util.Optional;

public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {

    Optional<StoreMember> findByUsername(String username);

    //Store db 가져오기
    @Query("select sm from StoreMember sm left outer join BasicUser bu on sm.id = bu.id " +
            "left outer join StoreMenu su on sm.id = su.id " +
            "left outer join StoreImageFile sif on sm.id = sif.id " +
            "where sm.id =:id")
    StoreMember getStoreById(Long id);
}
