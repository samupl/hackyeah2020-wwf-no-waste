package pl.wwf.nowaste.domain.product.reusage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wwf.nowaste.domain.category.Category;
import pl.wwf.nowaste.domain.product.Product;
import pl.wwf.nowaste.domain.tag.Tag;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reusage {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String title;

    private String author;

    private String description;

    private Integer upVotes;

    private Integer downVotes;

    @ElementCollection
    @CollectionTable(name = "join_reusage_photos", joinColumns = @JoinColumn(name = "reusage_id"))
    private Set<String> photos;

    @JsonIgnore
    @ManyToOne
    private Product product;

    @ManyToMany
    @JoinTable(name = "join_reusage_category",
            joinColumns = @JoinColumn(name = "reusage_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToMany
    @JoinTable(name = "join_reusage_tag",
            joinColumns = @JoinColumn(name = "reusage_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

}
