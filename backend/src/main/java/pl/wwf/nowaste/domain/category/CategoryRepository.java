package pl.wwf.nowaste.domain.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByName(String name);

    Set<Category> findAllByIdIn(Set<Long> id);

    Set<Category> findAllByNameIn(Set<String> names);

    Optional<Category> findByName(String name);
}
