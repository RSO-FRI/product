package si.fri.rso.models.converters;

import si.fri.rso.models.entities.ProductEntity;
import si.fri.rso.lib.Product;

public class ProductConverter {

    public static Product toDto(ProductEntity entity) {

        Product dto = new Product();
        dto.setProductId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setBrand(entity.getBrand());
        dto.setCreated(entity.getCreated());
        dto.setImageLink(entity.getImageLink());
        dto.setPrice(entity.getPrice());
        
        return dto;

    }

    public static ProductEntity toEntity(Product dto) {

        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getProductId());
        entity.setTitle(dto.getTitle());
        entity.setBrand(dto.getBrand());
        entity.setCreated(dto.getCreated());
        entity.setImageLink(dto.getImageLink());
        entity.setPrice(dto.getPrice());

        return entity;

    }

}
