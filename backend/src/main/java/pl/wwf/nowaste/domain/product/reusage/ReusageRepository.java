package pl.wwf.nowaste.domain.product.reusage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ReusageRepository extends JpaRepository<Reusage, Long> {

    @Query("FROM Reusage r JOIN FETCH r.product p WHERE p.id = ?1")
    Set<Reusage> findByProductId(Long productId);

    @Query("FROM Reusage  r" +
            " JOIN FETCH r.tags t" +
            " WHERE t.id IN (?1)")
    Set<Reusage> findByTags(Set<Long> tags);
}
