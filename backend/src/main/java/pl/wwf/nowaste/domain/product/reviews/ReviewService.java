package pl.wwf.nowaste.domain.product.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.product.Product;
import pl.wwf.nowaste.domain.product.ProductService;
import pl.wwf.nowaste.domain.product.reviews.web.ReviewCreateRequest;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final static int MIN_RATIO = 1;
    private final static int MAX_RATIO = 5;

    private final ReviewRepository repository;
    private final ProductService productService;

    public Review create(ReviewCreateRequest request, Principal principal) {
        validateRequest(request);
        validateReviewUnique(request.getProductId(), principal);

        final Product product = productService.findById(request.getProductId());
        return repository.save(Review.builder()
                .date(LocalDateTime.now())
                .author(getAuthor(principal))
                .product(product)
                .boxRating(request.getBoxRating())
                .boxReusabilityRating(request.getBoxReusabilityRating())
                .productReusabilityRating(request.getProductReusabilityRating())
                .comment(request.getComment())
                .build());
    }

    public List<Review> findByProjectId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Project ID must be present.");
        }
        return repository.findAllByProjectId(id).stream()
                .sorted(comparing(Review::getDate).reversed())
                .collect(toList());
    }

    private String getAuthor(Principal principal) {
        return "null";
    }

    private void validateRequest(ReviewCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request not present.");
        }
        if (request.getBoxRating() == null || !validateRatingNumber(request.getBoxRating())) {
            throw new IllegalArgumentException("Wrong Box Rating Value");
        }
        if (request.getBoxReusabilityRating() == null || !validateRatingNumber(request.getBoxReusabilityRating())) {
            throw new IllegalArgumentException("Wrong Box Reusability Rating Value");
        }
        if (request.getProductReusabilityRating() != null) {
            if (!validateRatingNumber(request.getProductReusabilityRating())) {
                throw new IllegalArgumentException("Wrong Product reusability Rating Value");
            }
        }
    }

    private boolean validateRatingNumber(Integer rating) {
        return MIN_RATIO <= rating && rating <= MAX_RATIO;
    }

    private void validateReviewUnique(Long productId, Principal principal) {
        final Product product = productService.findById(productId);
    }
}
