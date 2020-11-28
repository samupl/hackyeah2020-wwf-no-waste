package pl.wwf.nowaste.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByBarCode(String barCode);

    Optional<Product> findByBarCode(String barcode);
}
