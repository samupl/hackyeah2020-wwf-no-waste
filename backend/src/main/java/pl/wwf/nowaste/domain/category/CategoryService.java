package pl.wwf.nowaste.domain.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.category.web.CategoryCreateRequest;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category create(CategoryCreateRequest request) {
        if (request == null || request.getName() == null || repository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Empty parameter or category with this name already exists.");
        }

        return repository.save(Category.builder()
                .name(request.getName())
                .build());
    }

    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Category doesn't exist.");
        }
        repository.deleteById(id);
    }

}
