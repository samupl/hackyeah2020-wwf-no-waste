package pl.wwf.nowaste.domain.product.ratings;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.product.reviews.Review;
import pl.wwf.nowaste.domain.product.reviews.ReviewRepository;

import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final ReviewRepository reviewRepository;

    public RatingsAverage computeAverageRatings(Long productId) {
        final Set<Rating> ratings = reviewRepository.findAllByProductId(productId).stream().map(Review::getRating)
                .filter(Objects::nonNull)
                .collect(toSet());

        return RatingsAverage.builder()
                .boxReusable(average(ratings, Rating::getBoxReusable))
                .boxRecycable(average(ratings, Rating::getBoxRecycable))
                .boxFromRecycling(average(ratings, Rating::getBoxFromRecycling))
                .productReusable(average(ratings, Rating::getProductReusable))
                .productRecycable(average(ratings, Rating::getProductRecycable))
                .productFromRecycling(average(ratings, Rating::getProductFromRecycling))
                .repairable(average(ratings, Rating::getRepairable))
                .build();
    }

    private Double average(Set<Rating> ratings, Function<Rating, Integer> function) {
        return ratings.stream().mapToDouble(rating -> Double.valueOf(function.apply(rating)))
                .average().orElse(0);
    }
}
