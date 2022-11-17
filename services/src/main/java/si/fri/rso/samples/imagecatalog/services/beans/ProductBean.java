package si.fri.rso.samples.imagecatalog.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import si.fri.rso.samples.imagecatalog.lib.Product;
import si.fri.rso.samples.imagecatalog.models.converters.ProductMetadataConverter;
import si.fri.rso.samples.imagecatalog.models.entities.ProductEntity;


@RequestScoped
public class ProductBean {

    private Logger log = Logger.getLogger(ProductBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Product> get() {

        TypedQuery<ProductEntity> query = em.createNamedQuery(
                "ProductEntity.getAll", ProductEntity.class);

        List<ProductEntity> resultList = query.getResultList();

        return resultList.stream().map(ProductMetadataConverter::toDto).collect(Collectors.toList());

    }


    public Product getProduct(Integer id) {

        ProductEntity productEntity = em.find(ProductEntity.class, id);

        if (productEntity == null) {
            throw new NotFoundException();
        }

        Product product = ProductMetadataConverter.toDto(productEntity);

        return product;
    }

    public Product createProduct(Product product) {

        ProductEntity productEntity = ProductMetadataConverter.toEntity(product);

        try {
            beginTx();
            em.persist(productEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (productEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ProductMetadataConverter.toDto(productEntity);
    }

    public Product putImageMetadata(Integer id, Product product) {

        ProductEntity c = em.find(ProductEntity.class, id);

        if (c == null) {
            return null;
        }

        ProductEntity updatedProductEntity = ProductMetadataConverter.toEntity(product);

        try {
            beginTx();
            updatedProductEntity.setId(c.getId());
            updatedProductEntity = em.merge(updatedProductEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ProductMetadataConverter.toDto(updatedProductEntity);
    }

    public boolean deleteProduct(Integer id) {

        ProductEntity product = em.find(ProductEntity.class, id);

        if (product != null) {
            try {
                beginTx();
                em.remove(product);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
