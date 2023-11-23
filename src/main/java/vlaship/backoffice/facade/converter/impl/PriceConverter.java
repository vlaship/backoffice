package vlaship.backoffice.facade.converter.impl;

import org.springframework.lang.NonNull;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.exception.WrongProductInPriceException;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.facade.converter.BackOfficeConverter;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PriceConverter implements BackOfficeConverter<Price, PriceDto> {

	@NonNull
	@Override
	public PriceDto convert(@NonNull final Price price) {
		PriceDto dto = new PriceDto();

		dto.setId(price.getId());
		dto.setAmount(price.getAmount());
		dto.setCurrency(price.getCurrency().getCurrencyCode());
		if (price.getProduct() != null) {
			dto.setProductId(price.getProduct().getId());
		}
		return dto;
	}

	@NonNull
	public Price convert(@NonNull final PriceDto priceDto) {
		Price price = new Price();
		price.setAmount(priceDto.getAmount());
		price.setCurrency(Currency.getInstance(priceDto.getCurrency().toUpperCase()));
		return price;
	}

	@NonNull
	@Override
	public Price convert(@NonNull final PriceDto priceDto, @NonNull final Price price) {
		if (priceDto.getProductId() != null && !price.getProduct().getId().equals(priceDto.getProductId())) {
			throw new WrongProductInPriceException(priceDto);
		}

		price.setAmount(priceDto.getAmount());
		price.setCurrency(Currency.getInstance(priceDto.getCurrency().toUpperCase()));

		return price;
	}

}
