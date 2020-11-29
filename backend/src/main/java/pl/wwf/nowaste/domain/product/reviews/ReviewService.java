package pl.wwf.nowaste.domain.product.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.product.Product;
import pl.wwf.nowaste.domain.product.ProductService;
import pl.wwf.nowaste.domain.product.ratings.Rating;
import pl.wwf.nowaste.domain.product.reviews.web.ReviewCreateRequest;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static pl.wwf.nowaste.web.ValidationUtils.check;
import static pl.wwf.nowaste.web.ValidationUtils.checkNotNull;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final static int MIN_RATIO = 0;
    private final static int MAX_RATIO = 5;

    private final ReviewRepository repository;
    private final ProductService productService;

    public Review findById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Review> findAll() {
        return repository.findAll();
    }

    public List<Review> findByProductId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Project ID must be present.");
        }
        return repository.findAllByProductId(id).stream()
                .sorted(comparing(Review::getDate).reversed())
                .collect(toList());
    }

    public Review create(ReviewCreateRequest request) {
        validateRequest(request);

        final Product product = productService.findById(request.getProductId());
        return repository.save(Review.builder()
                .date(LocalDateTime.now())
                .author(request.getAuthor())
                .product(product)
                .comment(request.getComment())
                .rating(Rating.builder()
                        .boxReusable(request.getBoxReusable())
                        .boxRecycable(request.getBoxRecycable())
                        .boxFromRecycling(request.getBoxFromRecycling())
                        .productReusable(request.getProductReusable())
                        .productRecycable(request.getProductRecycable())
                        .productFromRecycling(request.getProductFromRecycling())
                        .repairable(request.getRepairable())
                        .build())
                .build());
    }

    public void delete(Long id) {
        checkNotNull(id, "Review ID to delete is empty.");
        check(repository.existsById(id), "Review not present");

        repository.deleteById(id);
    }

    private void validateRequest(ReviewCreateRequest request) {
        checkNotNull(request, "Request not present.");
        checkNotNull(request.getAuthor(), "Request Product ID is not present");
        checkNotNull(request.getBoxReusable(), "Request boxReusable not present.");
        checkNotNull(request.getBoxRecycable(), "Request boxRecycable not present.");
        checkNotNull(request.getBoxFromRecycling(), "Request boxFromRecycling not present.");
        checkNotNull(request.getProductReusable(), "Request productReusable not present.");
        checkNotNull(request.getProductRecycable(), "Request productRecycable not present.");
        checkNotNull(request.getProductFromRecycling(), "Request productFromRecycling not present.");
        checkNotNull(request.getRepairable(), "Request repairable not present.");

        check(validateRatingNumber(request.getBoxReusable()), "Request boxReusable illegal value.");
        check(validateRatingNumber(request.getBoxRecycable()), "Request boxRecycable illegal value.");
        check(validateRatingNumber(request.getBoxFromRecycling()), "Request boxFromRecycling illegal value.");
        check(validateRatingNumber(request.getProductReusable()), "Request productReusable illegal value.");
        check(validateRatingNumber(request.getProductRecycable()), "Request productRecycable illegal value.");
        check(validateRatingNumber(request.getProductFromRecycling()), "Request productFromRecycling illegal value.");
        check(validateRatingNumber(request.getRepairable()), "Request repairable illegal value.");

    }

    private boolean validateRatingNumber(Integer rating) {
        return MIN_RATIO <= rating && rating <= MAX_RATIO;
    }

}