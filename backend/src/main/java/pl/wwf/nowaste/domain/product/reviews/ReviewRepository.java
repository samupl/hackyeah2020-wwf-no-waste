package pl.wwf.nowaste.domain.product.reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("FROM Review r JOIN FETCH r.product p WHERE p.id = ?1")
    List<Review> findAllByProductId(Long id);
}
