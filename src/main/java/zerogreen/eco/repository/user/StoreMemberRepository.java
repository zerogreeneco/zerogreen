package zerogreen.eco.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.entity.userentity.StoreMember;
import zerogreen.eco.entity.userentity.UserRole;
import zerogreen.eco.repository.list.StoreListRepository;

import java.util.List;
import java.util.Optional;

public interface StoreMemberRepository extends JpaRepository<StoreMember, Long>,
        StoreMemberRepositoryCustom, StoreListRepository{

    Optional<StoreMember> findByUsername(String username);


    //승인받은 스토어
    @Query("select sm from StoreMember sm left outer join BasicUser bu on sm.id = bu.id " +
            "where bu.userRole ='STORE' ")
    List<StoreMember> findByApprovedStore(UserRole userRole);


//    @Query("select store from StoreMember store left outer join BasicUser user on store.id = user.id " +
//            "where user.userRole = 'STORE' and store.storeType = 'ECO_SHOP' ")
//    Slice<StoreMember> getShopList(RequestPageSortDto requestPageDto);
}
