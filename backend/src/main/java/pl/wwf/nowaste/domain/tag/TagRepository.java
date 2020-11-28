package pl.wwf.nowaste.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByName(String name);

    Set<Tag> findAllByIdIn(Set<Long> ids);

}
