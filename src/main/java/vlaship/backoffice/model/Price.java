package vlaship.backoffice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "product")
public class Price implements IModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Price(final BigDecimal amount, final Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
