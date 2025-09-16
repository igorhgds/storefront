package henrique.igor.storefront.mapper;

import henrique.igor.storefront.dto.ProductInfoDTO;
import henrique.igor.storefront.entity.ProductEntity;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface IProductMapper {

    ProductInfoDTO toDTO(final ProductEntity entity, final BigDecimal price);
}
