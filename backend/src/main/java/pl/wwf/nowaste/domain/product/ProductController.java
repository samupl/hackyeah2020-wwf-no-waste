package pl.wwf.nowaste.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.wwf.nowaste.domain.product.web.ProductCreateRequest;
import pl.wwf.nowaste.domain.product.web.ProductDetailsMainView;
import pl.wwf.nowaste.domain.product.web.ProductUpdateRequest;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

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

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public ProductDetailsMainView create(@RequestPart("product") ProductCreateRequest request, @RequestPart(name = "photo", required = false) MultipartFile photo) {
        return service.create(request, photo);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public ProductDetailsMainView create(@RequestBody ProductCreateRequest request) {
        return service.create(request, null);
    }

    @PostMapping("/{id}")
    public ProductDetailsMainView update(@PathVariable Long id, @RequestBody ProductUpdateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
