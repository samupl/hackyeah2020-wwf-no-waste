package pl.wwf.nowaste.domain.product.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {

    private String name;

    private String barCode;

    private Long categoryId;

    private Set<Long> tags;

}
