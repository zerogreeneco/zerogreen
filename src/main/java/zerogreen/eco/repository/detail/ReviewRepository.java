package zerogreen.eco.repository.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import zerogreen.eco.entity.detail.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}