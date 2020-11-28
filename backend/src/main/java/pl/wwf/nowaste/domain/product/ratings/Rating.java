package pl.wwf.nowaste.domain.product.ratings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Rating {

    private Integer boxReusable;

    private Integer boxRecycable;

    private Integer boxFromRecycling;

    private Integer productReusable;

    private Integer productRecycable;

    private Integer productFromRecycling;

    private Integer repairable;

}
