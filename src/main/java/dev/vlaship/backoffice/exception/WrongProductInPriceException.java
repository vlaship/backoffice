package dev.vlaship.backoffice.exception;

import dev.vlaship.backoffice.dto.PriceDto;

public class WrongProductInPriceException extends AbstractException {

    public WrongProductInPriceException(final PriceDto priceDto) {
        super("Wrong Product in Price " + priceDto);
    }

}
