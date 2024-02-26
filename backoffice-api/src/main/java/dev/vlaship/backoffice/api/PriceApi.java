package dev.vlaship.backoffice.api;

import dev.vlaship.backoffice.dto.BetweenPrice;
import dev.vlaship.backoffice.dto.PriceDto;
import dev.vlaship.backoffice.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
@Tag(name = "price")
@RequestMapping("/api/price")
public interface PriceApi {

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @PutMapping("/update")
    ResponseEntity<PriceDto> update(@Valid final @RequestBody PriceDto dto);

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> delete(final @PathVariable("id") Long id);

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<PriceDto> find(final @PathVariable("id") Long id);

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/list")
    ResponseEntity<List<PriceDto>> findAll(final Pageable pageable);

    @Operation(
            operationId = "findAllPricesBetweenAndCurrency",
            summary = "find all prices between and currency",
            tags = {"price"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/between/{currency}/{from}/{to}")
    ResponseEntity<List<PriceDto>> findAllBetween(
            final @PathVariable("currency") String currency,
            final @PathVariable("from") BigDecimal from,
            final @PathVariable("to") BigDecimal to,
            final Pageable pageable
    );

    @Operation(
            operationId = "findAllPricesBetween",
            summary = "find all prices between",
            tags = {"price"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/between")
    ResponseEntity<List<PriceDto>> findAllBetween(
            @Valid final @RequestBody BetweenPrice betweenPrice,
            final Pageable pageable
    );

    @Operation(
            operationId = "findAllPricesByCurrency",
            summary = "find all prices by currency",
            tags = {"price"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/currency/{currency}")
    ResponseEntity<List<PriceDto>> findAllByCurrency(
            final @PathVariable("currency") String currency,
            final Pageable pageable
    );

    @Operation(
            operationId = "findAllPricesByProduct",
            summary = "find all prices by product",
            tags = {"price"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/product/{productId}")
    ResponseEntity<List<PriceDto>> findAllByProduct(
            final @PathVariable("productId") Long productId,
            final Pageable pageable
    );

    @Operation(
            operationId = "findAllPricesByProduct",
            summary = "find all prices by product",
            tags = {"price"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    }),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = ProblemDetail.class))
                    })
            }
    )
    @GetMapping("/product")
    ResponseEntity<List<PriceDto>> findAllByProduct(
            @Valid final @RequestBody ProductDto productDto,
            final Pageable pageable
    );
}
