package pl.wwf.nowaste.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.wwf.nowaste.domain.category.CategoryService;
import pl.wwf.nowaste.domain.product.api.ProductDBApiClient;
import pl.wwf.nowaste.domain.product.photo.PhotoService;
import pl.wwf.nowaste.domain.product.ratings.RatingService;
import pl.wwf.nowaste.domain.product.web.ProductCreateRequest;
import pl.wwf.nowaste.domain.product.web.ProductDetailsMainView;
import pl.wwf.nowaste.domain.tag.TagService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static pl.wwf.nowaste.web.ValidationUtils.check;
import static pl.wwf.nowaste.web.ValidationUtils.checkNotNull;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final RatingService ratingService;
    private final PhotoService photoService;
    private final ProductDBApiClient apiClient;

    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public ProductDetailsMainView findDetailsById(Long id) {
        Product product = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return convertToDetailsMainView(product);
    }

    public ProductDetailsMainView findDetailsByBarcode(String barcode) {
        if (barcode == null) {
            throw new IllegalArgumentException("Empty barcode.");
        }

        if (!repository.existsByBarCode(barcode)) {
            final Product product = apiClient.downloadProductInfo(barcode);
            checkNotNull(product, "Could not find product info - incorrect bar code");
            return convertToDetailsMainView(repository.save(product));
        }

        return repository.findByBarCode(barcode)
                .map(this::convertToDetailsMainView)
                .get();
    }

    public boolean existById(Long id) {
        return id != null && repository.existsById(id);
    }

    public ProductDetailsMainView create(ProductCreateRequest request, MultipartFile file) {
        checkNotNull(request, "Empty product part request");
        checkNotNull(request.getName(), "Empty product name");
        checkNotNull(request.getBarCode(), "Empty product barCode");
        checkNotNull(request.getCategoryId(), "Empty product category");
        check(!repository.existsByBarCode(request.getBarCode()), "Product with this barcode already exists.");

        final String photoId = photoService.uploadFile(file);

        return convertToDetailsMainView(repository.save(Product.builder()
                .name(request.getName())
                .barCode(request.getBarCode())
                .photo(photoId)
                .category(categoryService.findById(request.getCategoryId()))
                .tags(tagService.findAllByIdIn(request.getTags()))
                .build()));
    }

    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) {
            throw new IllegalArgumentException("Product doesn't exist.");
        }
        repository.deleteById(id);
    }

    private ProductDetailsMainView convertToDetailsMainView(Product product) {
        return ProductDetailsMainView.builder()
                .id(product.getId())
                .barcode(product.getBarCode())
                .name(product.getName())
                .photoUrl(photoService.createPhotoUrl(product.getPhoto()))
                .averageRatings(ratingService.computeAverageRatings(product.getId()))
                .category(product.getCategory())
                .tags(product.getTags())
                .build();
    }
}
