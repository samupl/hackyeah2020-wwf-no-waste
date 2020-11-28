package pl.wwf.nowaste.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wwf.nowaste.domain.category.CategoryService;
import pl.wwf.nowaste.domain.product.photo.PhotoService;
import pl.wwf.nowaste.domain.product.ratings.RatingService;
import pl.wwf.nowaste.domain.product.web.ProductCreateRequest;
import pl.wwf.nowaste.domain.product.web.ProductDetailsMainView;
import pl.wwf.nowaste.domain.tag.TagService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final RatingService ratingService;
    private final PhotoService photoService;

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

        final Optional<Product> product = repository.findByBarCode(barcode);

        return product
                .map(this::convertToDetailsMainView)
                .orElseThrow(() -> new EntityNotFoundException("Product with this Bar Code doesn't exist."));
    }

    public boolean existById(Long id) {
        return id != null && repository.existsById(id);
    }

    public Product create(ProductCreateRequest request) {
        if (request == null ||
                request.getName() == null ||
                request.getBarCode() == null ||
                request.getCategoryId() == null ||
                repository.existsByBarCode(request.getBarCode())) {
            throw new IllegalArgumentException("Empty parameter or product with this barcode already exists.");
        }

        return repository.save(Product.builder()
                .name(request.getName())
                .barCode(request.getBarCode())
                .category(categoryService.findById(request.getCategoryId()))
                .tags(tagService.findAllByIdIn(request.getTags()))
                .build());
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
                .photoUrl(photoService.createPhotoUrl(product.getId()))
                .averageRatings(ratingService.computeAverageRatings(product.getId()))
                .category(product.getCategory())
                .tags(product.getTags())
                .build();
    }
}
