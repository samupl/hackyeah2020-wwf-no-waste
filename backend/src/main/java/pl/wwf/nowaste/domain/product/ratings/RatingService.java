package pl.wwf.nowaste.domain.product.ratings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.product.reviews.Review;
import pl.wwf.nowaste.domain.product.reviews.ReviewRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final ReviewRepository reviewRepository;

    public RatingsAverage computeAverageRatings(Long productId) {
        final List<Review> reviews = reviewRepository.findAllByProductId(productId);

        return RatingsAverage.builder()
                .boxRating(average(reviews.stream().map(Review::getBoxRating)))
                .boxReusabilityRating(average(reviews.stream().map(Review::getBoxReusabilityRating)))
                .productReusabilityRating(average(reviews.stream().map(Review::getProductReusabilityRating)))
                .build();
    }

    private Double average(Stream<Integer> stream) {
        return stream.filter(Objects::nonNull).mapToDouble(Double::valueOf).average().orElse(0);
    }
}
