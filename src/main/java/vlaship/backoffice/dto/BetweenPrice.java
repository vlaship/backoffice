package vlaship.backoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetweenPrice implements Serializable {

    @NotNull
    @Positive(message = "Amount must be positive")
    private BigDecimal from;

    @NotNull
    @Positive(message = "Amount must be positive")
    private BigDecimal to;

    @NotNull
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;

}
