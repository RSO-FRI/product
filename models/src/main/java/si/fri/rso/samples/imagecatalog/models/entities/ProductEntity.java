package si.fri.rso.samples.imagecatalog.models.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product")
@NamedQueries(value =
        {
                @NamedQuery(name = "ProductEntity.getAll",
                        query = "SELECT pr FROM ProductEntity pr")
        })
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "brand")
    private String brand;

    @Column(name = "created")
    private Instant created;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "price")
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String image_link) {
        this.imageLink = image_link;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}