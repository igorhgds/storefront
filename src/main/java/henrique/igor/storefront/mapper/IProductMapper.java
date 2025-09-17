package henrique.igor.storefront.mapper;

import henrique.igor.storefront.controller.request.ProductSaveRequest;
import henrique.igor.storefront.controller.response.ProductAvailableResponse;
import henrique.igor.storefront.controller.response.ProductDetailResponse;
import henrique.igor.storefront.controller.response.ProductSavedResponse;
import henrique.igor.storefront.dto.ProductInfoDTO;
import henrique.igor.storefront.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IProductMapper {

    ProductInfoDTO toDTO(final ProductEntity entity, final BigDecimal price);

    @Mapping(target = "active", constant = "false")
    ProductEntity toEntity(final ProductSaveRequest request);

    ProductSavedResponse toResponse(final ProductEntity entity);

    List<ProductAvailableResponse> toResponse(final List<ProductEntity> entities);

    ProductDetailResponse toResponse(final ProductInfoDTO dto);
}
