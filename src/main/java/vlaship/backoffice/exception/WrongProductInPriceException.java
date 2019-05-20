package vlaship.backoffice.exception;

import vlaship.backoffice.dto.PriceDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongProductInPriceException extends AbstractException {

    public WrongProductInPriceException(final PriceDto priceDto) {
        super("Wrong Product in Price " + priceDto);
    }
}
