package dev.vlaship.backoffice.mapper;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.exception.WrongProductInPriceException;
import dev.vlaship.backoffice.model.Price;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PriceMapper {

    @NonNull
    public PriceDto map(@NonNull final Price price) {
        var builder = new PriceDto()
                .id(price.getId())
                .amount(price.getAmount())
                .currency(price.getCurrency().getCurrencyCode());

        if (price.getProduct() != null) {
            builder.productId(price.getProduct().getId());
        }

        return builder;
    }

    @NonNull
    public Price map(@NonNull final PriceDto priceDto) {
        return Price.builder()
                .amount(priceDto.getAmount())
                .currency(Currency.getInstance(priceDto.getCurrency().toUpperCase()))
                .build();
    }

    @NonNull
    public Price merge(@NonNull final PriceDto priceDto, @NonNull final Price price) {
        if (priceDto.getProductId() != null && !price.getProduct().getId().equals(priceDto.getProductId())) {
            throw new WrongProductInPriceException(priceDto);
        }

        price.setAmount(priceDto.getAmount());
        price.setCurrency(Currency.getInstance(priceDto.getCurrency().toUpperCase()));

        return price;
    }

}
