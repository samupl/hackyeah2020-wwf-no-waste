package pl.wwf.nowaste.domain.product.reusage.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReusageCreateRequest {

    private String title;
    private String description;
    private String author;
    private Set<String> photos;
    private Long productId;
    private Set<Long> categories;
    private Set<Long> tags;


}







