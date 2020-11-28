package pl.wwf.nowaste.domain.product.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.wwf.nowaste.domain.product.reviews.web.ReviewCreateRequest;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PutMapping
    @ResponseStatus(CREATED)
    public Review create(@RequestBody ReviewCreateRequest request, Principal principal) {
        return service.create(request, principal);
    }

    @GetMapping("/find-by-project/{id}")
    @ResponseStatus(CREATED)
    public List<Review> findByProjectId(@PathVariable Long id) {
        return service.findByProjectId(id);
    }

}
