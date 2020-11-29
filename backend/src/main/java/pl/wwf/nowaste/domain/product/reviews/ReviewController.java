package pl.wwf.nowaste.domain.product.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.wwf.nowaste.domain.product.reviews.web.ReviewCreateRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @GetMapping("/{id}")
    public Review findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/all")
    public List<Review> findAll() {
        return service.findAll();
    }

    @GetMapping("/find-by-product/{id}")
    public List<Review> findByProductId(@PathVariable Long id) {
        return service.findByProductId(id);
    }

    @PutMapping
    @ResponseStatus(CREATED)
    public Review create(@RequestBody ReviewCreateRequest request) {
        return service.create(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
