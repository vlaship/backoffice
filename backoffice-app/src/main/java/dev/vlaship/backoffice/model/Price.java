package dev.vlaship.backoffice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prices")
public class Price implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    public Price(final BigDecimal amount, final Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy p ? p.getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy p ? p.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Price price = (Price) o;
        return getId() != null && Objects.equals(getId(), price.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy p ? p.getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
