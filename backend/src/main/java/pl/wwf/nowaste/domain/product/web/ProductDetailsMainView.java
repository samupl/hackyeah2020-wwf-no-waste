package pl.wwf.nowaste.domain.product.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wwf.nowaste.domain.category.Category;
import pl.wwf.nowaste.domain.product.ratings.RatingsAverage;
import pl.wwf.nowaste.domain.tag.Tag;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ProductDetailsMainView {

    private Long id;
    private String barcode;
    private String name;
    private String photoUrl;
    private RatingsAverage averageRatings;
    private Category category;
    private Set<Tag> tags;

}
