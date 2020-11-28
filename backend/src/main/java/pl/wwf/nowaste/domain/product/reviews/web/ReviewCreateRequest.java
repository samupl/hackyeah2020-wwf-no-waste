package pl.wwf.nowaste.domain.product.reviews.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    private Long productId;
    private Integer boxRating;
    private Integer boxReusabilityRating;
    private Integer productReusabilityRating;
    private String comment;

}