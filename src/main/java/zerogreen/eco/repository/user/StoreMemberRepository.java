package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.dto.store.StoreDto;
import zerogreen.eco.entity.userentity.StoreMember;

import java.util.List;

public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {

    //승인받은 스토어
    @Query("select sm from StoreMember sm where sm.userRole ='STORE'")
    List<StoreDto> findByApprovedStore();

}
