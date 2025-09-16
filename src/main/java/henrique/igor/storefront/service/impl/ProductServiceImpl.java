package henrique.igor.storefront.service.impl;

import henrique.igor.storefront.dto.ProductInfoDTO;
import henrique.igor.storefront.entity.ProductEntity;
import henrique.igor.storefront.repository.ProductRepository;
import henrique.igor.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;

    @Override
    public ProductEntity save(ProductEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void chanceActivated(UUID id, boolean active) {
        var entity = findById(id);
        entity.setActive(active);
        repository.save(entity);
    }

    @Override
    public List<ProductEntity> findAllActive() {
        return repository.findByActiveTrueOrderByNameAsc();
    }

    @Override
    public ProductInfoDTO findInfo(UUID id) {
        return null;
    }

    @Override
    public void purchase(UUID id) {

    }

    private ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }
}
