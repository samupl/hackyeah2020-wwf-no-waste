package pl.wwf.nowaste.domain.product.ratings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingsAverage {

    private Double boxRating;
    private Double boxReusabilityRating;
    private Double productReusabilityRating;

}
