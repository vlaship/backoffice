package vlaship.backoffice.facade.converter.impl;

import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.exception.WrongProductInPriceException;
import vlaship.backoffice.model.Price;
import vlaship.backoffice.facade.converter.IConverter;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PriceConverter implements IConverter<Price, PriceDto> {

    @Override
    public PriceDto convert(final Price price) {
        isNull(price, Price.class.getSimpleName());

        PriceDto dto = new PriceDto();

        dto.setId(price.getId());
        dto.setAmount(price.getAmount());
        dto.setCurrency(price.getCurrency().getCurrencyCode());
        if (price.getProduct() != null) {
            dto.setProductId(price.getProduct().getId());
        }
        return dto;
    }

    public Price convert(final PriceDto priceDto) {
        isNull(priceDto, PriceDto.class.getSimpleName());

        Price price = new Price();
        price.setAmount(priceDto.getAmount());
        price.setCurrency(Currency.getInstance(priceDto.getCurrency().toUpperCase()));
        return price;
    }

    @Override
    public Price convert(final PriceDto priceDto, final Price price) {
        isNull(priceDto, PriceDto.class.getSimpleName());
        isNull(price, Price.class.getSimpleName());

        if (priceDto.getProductId() != null && !price.getProduct().getId().equals(priceDto.getProductId())) {
            throw new WrongProductInPriceException(priceDto);
        }

        price.setAmount(priceDto.getAmount());
        price.setCurrency(Currency.getInstance(priceDto.getCurrency().toUpperCase()));

        return price;
    }
}
