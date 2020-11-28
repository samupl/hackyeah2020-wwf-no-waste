package pl.wwf.nowaste.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.wwf.nowaste.domain.product.web.ProductCreateRequest;
import pl.wwf.nowaste.domain.product.web.ProductDetailsMainView;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/all")
    public List<Product> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ProductDetailsMainView findDetailsById(@PathVariable Long id) {
        return service.findDetailsById(id);
    }

    @GetMapping("/find-by-barcode/{barcode}")
    public ProductDetailsMainView findByBarcode(@PathVariable String barcode) {
        return service.findDetailsByBarcode(barcode);
    }

    @PutMapping
    @ResponseStatus(CREATED)
    public ProductDetailsMainView create(@RequestPart("product") ProductCreateRequest request, @RequestPart(name = "photo", required = false) MultipartFile photo) {
        return service.create(request, photo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
