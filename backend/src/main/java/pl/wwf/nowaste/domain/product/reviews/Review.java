package pl.wwf.nowaste.domain.product.reviews;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wwf.nowaste.domain.product.Product;
import pl.wwf.nowaste.domain.product.ratings.Rating;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private LocalDateTime date;

    @Embedded
    private Rating rating;

    private String comment;

    private String author;

    @JsonIgnore
    @ManyToOne
    private Product product;

}
