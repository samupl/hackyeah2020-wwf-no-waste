package pl.wwf.nowaste.domain.product.ratings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingsAverage {

    private Integer boxRating;
    private Integer boxReusabilityRating;
    private Integer productReusabilityRating;

}
