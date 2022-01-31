package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import zerogreen.eco.entity.detail.Likes;
import zerogreen.eco.entity.userentity.StoreMember;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("select count(l.id) from Likes l where l.storeMember =:storeMember")
    Long counting(StoreMember storeMember);

    @Query("select l from Likes l where l.basicUser =:basicUser and l.storeMember =:storeMember")
    Likes getLikesByStore(StoreMember storeMember);
}
