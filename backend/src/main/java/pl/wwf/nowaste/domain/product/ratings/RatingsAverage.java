package pl.wwf.nowaste.domain.product.ratings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RatingsAverage {

    private Double boxReusable;
    private Double boxRecycable;
    private Double boxFromRecycling;
    private Double productReusable;
    private Double productRecycable;
    private Double productFromRecycling;
    private Double repairable;

}
