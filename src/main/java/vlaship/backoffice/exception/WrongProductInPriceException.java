package vlaship.backoffice.exception;

import vlaship.backoffice.dto.PriceDto;

public class WrongProductInPriceException extends AbstractException {

	public WrongProductInPriceException(final PriceDto priceDto) {
		super("Wrong Product in Price " + priceDto);
	}

}
