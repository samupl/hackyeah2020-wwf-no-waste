package pl.wwf.nowaste.domain.product.ratings;

import org.springframework.stereotype.Service;

@Service
public class RatingService {

    public RatingsAverage computeAverageRatings(Long id) {
        return RatingsAverage.builder()
                .boxRating(1)
                .boxReusabilityRating(2)
                .productReusabilityRating(3)
                .build();
    }
}
