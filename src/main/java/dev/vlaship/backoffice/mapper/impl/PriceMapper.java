package dev.vlaship.backoffice.mapper.impl;

import org.springframework.lang.NonNull;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.exception.WrongProductInPriceException;
import dev.vlaship.backoffice.model.Price;
import dev.vlaship.backoffice.mapper.BackOfficeMapper;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service
public class PriceMapper implements BackOfficeMapper<Price, PriceDto> {

    @NonNull
    @Override
    public PriceDto map(@NonNull final Price price) {
        var builder = PriceDto.builder()
                .id(price.getId())
                .amount(price.getAmount())
                .currency(price.getCurrency().getCurrencyCode());

        if (price.getProduct() != null) {
            builder.productId(price.getProduct().getId());
        }

        return builder.build();
    }

    @NonNull
    public Price map(@NonNull final PriceDto priceDto) {
        return Price.builder()
                .amount(priceDto.amount())
                .currency(Currency.getInstance(priceDto.currency().toUpperCase()))
                .build();
    }

    @NonNull
    @Override
    public Price merge(@NonNull final PriceDto priceDto, @NonNull final Price price) {
        if (priceDto.productId() != null && !price.getProduct().getId().equals(priceDto.productId())) {
            throw new WrongProductInPriceException(priceDto);
        }

        price.setAmount(priceDto.amount());
        price.setCurrency(Currency.getInstance(priceDto.currency().toUpperCase()));

        return price;
    }

}
