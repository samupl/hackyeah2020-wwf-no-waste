package pl.wwf.nowaste.domain.product.reusage.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wwf.nowaste.domain.category.Category;
import pl.wwf.nowaste.domain.tag.Tag;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReusageDetails {

    private Long id;
    private LocalDateTime date;
    private String title;
    private String author;
    private String description;
    private Integer upVotes;
    private Integer downVotes;
    private Set<String> photosUrl;
    private Set<Category> categories;
    private Set<Tag> tags;

}
