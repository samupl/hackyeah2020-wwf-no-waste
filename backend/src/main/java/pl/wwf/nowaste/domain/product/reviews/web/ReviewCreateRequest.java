package pl.wwf.nowaste.domain.product.reviews.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateRequest {

    private Long productId;
    private Integer boxReusable;
    private Integer boxRecycable;
    private Integer boxFromRecycling;
    private Integer productReusable;
    private Integer productRecycable;
    private Integer productFromRecycling;
    private Integer repairable;
    private String comment;
    private String author;

}