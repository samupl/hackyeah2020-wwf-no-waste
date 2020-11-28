package pl.wwf.nowaste.domain.product.reusage.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class ReusageCreateRequest {

    private String title;
    private String description;
    private Set<String> photos;
    private Long productId;
    private Set<Long> categories;
    private Set<Long> tags;


}







