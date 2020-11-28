package pl.wwf.nowaste.domain.product.api.eprodukty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiDetailsResponse {

    private String gtinNumber;
    private String gtinStatus;
    private String name;
    private Set<String> imageUrls;
    private Set<ApiDetailsCategoryResponse> categoryDetails;

}
