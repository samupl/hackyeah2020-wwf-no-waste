package pl.wwf.nowaste.domain.product.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.RestTemplate;
import pl.wwf.nowaste.domain.category.Category;
import pl.wwf.nowaste.domain.category.CategoryService;
import pl.wwf.nowaste.domain.category.web.CategoryCreateRequest;
import pl.wwf.nowaste.domain.product.Product;
import pl.wwf.nowaste.domain.product.api.eprodukty.ApiDetailsCategoryResponse;
import pl.wwf.nowaste.domain.product.api.eprodukty.ApiDetailsResponse;
import pl.wwf.nowaste.domain.product.api.eprodukty.ApiResponse;
import pl.wwf.nowaste.domain.product.api.google.GoogleImageDetailsResponse;
import pl.wwf.nowaste.domain.product.api.google.GoogleImageResponse;
import pl.wwf.nowaste.domain.product.photo.PhotoService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toSet;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class ProductDBApiClient {

    private final static String PRODUCT_API_QUERY_KEY = "gtin_number";

    private final static String GOOGLE_SEARCHTYPE_KEY = "searchType";
    private final static String GOOGLE_SEARCHTYPE_VALUE = "image";
    private final static String GOOGLE_KEY_KEY = "key";
    private final static String GOOGLE_QUERY_KEY = "q";
    private final static String GOOGLE_CX_KEY = "cx";

    @Value("${product.api.url}")
    private String apiUrl;

    @Value("${product.google.api.url}")
    private String googleApiUrl;

    @Value("${product.google.api.key}")
    private String googleApiKey;

    @Value("${product.google.api.cx}")
    private String googleCxKey;

    private final RestTemplate restTemplate;

    private final CategoryService categoryService;
    private final PhotoService photoService;

    public Product downloadProductInfo(String barcode) {
        if (barcode == null) {
            return null;
        }

        final ResponseEntity<ApiResponse> response = restTemplate.getForEntity(createProductUri(barcode), ApiResponse.class);
        if (response.getStatusCode() != OK || response.getBody() == null) {
            return null;
        }
        final Set<ApiDetailsResponse> results = response.getBody().getResults();
        if (isEmpty(results)) {
            return null;
        }
//        restTemplate.getForEntity("https://www.eprodukty.gs1.pl/api/v1/products/get_products/?gtin_number=05900334007780", ApiResponse.class);
        final String productName = productName(results).orElse(null);
        if (productName == null) {
            return null;
        }
        final String imageId = imageUrl(results).orElse(findImageInGoogle(productName));
        final Category category = findCategory(results);

        return Product.builder()
                .barCode(barcode)
                .name(productName)
                .photo(imageId)
                .category(category)
                .build();
    }

    private Category findCategory(Set<ApiDetailsResponse> results) {
        final Set<String> collect = results.stream()
                .map(ApiDetailsResponse::getCategoryDetails)
                .flatMap(Collection::parallelStream)
                .map(ApiDetailsCategoryResponse::getText)
                .collect(toSet());
        if (isEmpty(collect)) {
            return categoryService.findDefault();
        }
        final Set<Category> allExisted = categoryService.findAllByNameIn(collect);
        if (isEmpty(allExisted)) {
            return categoryService.create(CategoryCreateRequest.builder()
                    .name(collect.stream().min(String::compareTo).get())
                    .build());
        }
        return allExisted.stream().min(comparing(Category::getName)).get();
    }

    private Optional<String> productName(Set<ApiDetailsResponse> results) {
        return results.stream()
                .map(ApiDetailsResponse::getName)
                .filter(Objects::nonNull)
                .findFirst();
    }

    private String findImageInGoogle(String productName) {
        try {
            final GoogleImageResponse response = new ObjectMapper().readValue(new URL(createGoogleUri(productName)), GoogleImageResponse.class);
            if (response == null || isEmpty(response.getItems())) {
                return null;
            }
            byte[] image = null;
            for (final GoogleImageDetailsResponse imageDetails : response.getItems()) {
                ResponseEntity<byte[]> imageResponse = restTemplate.getForEntity(imageDetails.getLink(), byte[].class);
                if (imageResponse.getStatusCode() == OK) {
                    image = imageResponse.getBody();
                }
            }
            if (image == null) {
                return null;
            }
            return photoService.uploadFile(image, "jpg")
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private Optional<String> imageUrl(Set<ApiDetailsResponse> results) {
        Optional<String> url = results.stream()
                .map(ApiDetailsResponse::getImageUrls)
                .flatMap(Collection::parallelStream)
                .filter(Objects::nonNull)
                .findFirst();
        if (!url.isEmpty()) {
            byte[] fileContent = restTemplate.getForObject(url.get(), byte[].class);
            if (fileContent == null || fileContent.length == 0) {
                return Optional.empty();
            }
            return photoService.uploadFile(fileContent, "jpg");
        }
        return url;
    }

    private String createProductUri(String barcode) {
        try {
            final URIBuilder uriBuilder = new URIBuilder(apiUrl);
            uriBuilder.addParameter(PRODUCT_API_QUERY_KEY, barcode);
            return uriBuilder.build().toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String createGoogleUri(String query) {
        try {
            final URIBuilder uriBuilder = new URIBuilder(googleApiUrl);
            uriBuilder.addParameter(GOOGLE_KEY_KEY, googleApiKey);
            uriBuilder.addParameter(GOOGLE_CX_KEY, googleCxKey);
            uriBuilder.addParameter(GOOGLE_SEARCHTYPE_KEY, GOOGLE_SEARCHTYPE_VALUE);
            uriBuilder.addParameter(GOOGLE_QUERY_KEY, query);
            return uriBuilder.build().toString();
        } catch (Exception e) {
            return "";
        }
    }



}
