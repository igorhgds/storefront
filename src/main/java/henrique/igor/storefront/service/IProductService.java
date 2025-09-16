package henrique.igor.storefront.service;

import henrique.igor.storefront.dto.ProductInfoDTO;
import henrique.igor.storefront.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    ProductEntity save(final ProductEntity entity);

    void chanceActivated(final UUID id, final boolean active);

    List<ProductEntity> findAllActive();

    ProductInfoDTO findInfo(final UUID id);

    void purchase(final UUID id);
}
