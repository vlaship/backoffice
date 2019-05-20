package vlaship.backoffice.controller.impl;

import vlaship.backoffice.controller.AbstractController;
import vlaship.backoffice.dto.PriceDto;
import vlaship.backoffice.dto.ProductDto;
import vlaship.backoffice.dto.ProductCreationDto;
import vlaship.backoffice.facade.impl.ProductFacade;
import vlaship.backoffice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends AbstractController<Product, ProductDto> {

    private final ProductFacade facade;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto create(@Valid final @RequestBody ProductCreationDto productCreationDto) {
        return facade.create(productCreationDto);
    }

    @PutMapping("/{productId}/add/price")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto add(final @PathVariable("productId") Integer productId,
                          @Valid final @RequestBody PriceDto priceDto) {
        return facade.add(priceDto, productId);
    }

    @PutMapping("/{productId}/add/category/{categoryId}")
    public ProductDto add(final @PathVariable("productId") Integer productId,
                          final @PathVariable("categoryId") Integer categoryId) {
        return facade.add(categoryId, productId);
    }

    @PutMapping("/{productId}/remove/category/{categoryId}")
    public ProductDto removeCategory(final @PathVariable("productId") Integer productId,
                                     final @PathVariable("categoryId") Integer categoryId) {
        return facade.removeCategory(categoryId, productId);
    }

    @PutMapping("/{productId}/remove/price/{priceId}")
    public ProductDto removePrice(final @PathVariable("productId") Integer productId,
                                  final @PathVariable("priceId") Integer priceId) {
        return facade.removePrice(priceId, productId);
    }

    @GetMapping("/price")
    public List<ProductDto> findAllByPrice(@Valid final @RequestBody PriceDto priceDto,
                                           final Pageable pageable) {
        return facade.findAll(pageable, priceDto);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductDto> findAllByCategory(final @PathVariable("categoryId") Integer categoryId,
                                              final Pageable pageable) {
        return facade.findAll(pageable, categoryId);
    }

    @GetMapping("/name/{name}")
    public List<ProductDto> findAllName(final @PathVariable("name") String name,
                                        final Pageable pageable) {
        return facade.findAll(pageable, name);
    }

    @Autowired
    public ProductController(final ProductFacade facade) {
        super(facade);
        this.facade = facade;
    }
}
