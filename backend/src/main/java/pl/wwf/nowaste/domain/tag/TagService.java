package pl.wwf.nowaste.domain.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.tag.web.TagCreateRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    public Tag findById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Tag> findAll() {
        return repository.findAll();
    }

    public Tag create(TagCreateRequest request) {
        if (request == null || request.getName() == null || repository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Empty parameter or tag with this name already exists.");
        }

        return repository.save(Tag.builder()
                .name(request.getName())
                .build());
    }

    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Tag doesn't exist.");
        }
        repository.deleteById(id);
    }

    public Set<Tag> findAllByIdIn(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Set.of();
        }
        return repository.findAllByIdIn(tagIds);
    }
}
