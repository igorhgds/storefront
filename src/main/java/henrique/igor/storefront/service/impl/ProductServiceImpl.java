package henrique.igor.storefront.service.impl;

import henrique.igor.storefront.dto.ProductDetailsDTO;
import henrique.igor.storefront.dto.ProductInfoDTO;
import henrique.igor.storefront.entity.ProductEntity;
import henrique.igor.storefront.mapper.IProductMapper;
import henrique.igor.storefront.repository.ProductRepository;
import henrique.igor.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final RestClient warehouseClient;
    private final IProductMapper  mapper;

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
        var entity = findById(id);
        var price = requestCurrentAmount(id);
        return mapper.toDTO(entity, price);
    }

    @Override
    public void purchase(UUID id) {
        purchaseWarehouse(id);
    }

    private ProductEntity findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    private BigDecimal requestCurrentAmount(final UUID id) {
        var dto = warehouseClient.get()
                .uri("/products/" + id)
                .retrieve()
                .body(ProductDetailsDTO.class);
        return dto.price();
    }

    private void purchaseWarehouse(final UUID id) {
        var path = String.format("/products/%s/purchase", id);
        warehouseClient.post()
                .uri(path)
                .retrieve()
                .toBodilessEntity();
    }
}
