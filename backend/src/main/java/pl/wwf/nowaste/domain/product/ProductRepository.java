package pl.wwf.nowaste.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarCode(String barCode);

    Product findOneByBarCode(String barcode);
}
