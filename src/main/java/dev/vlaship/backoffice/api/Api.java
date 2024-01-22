package dev.vlaship.backoffice.api;

import dev.vlaship.backoffice.dto.Dto;
import dev.vlaship.backoffice.model.Model;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@SecurityRequirement(name = "Bearer Authentication")
public interface Api<M extends Model, D extends Dto> {

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
    ResponseEntity<D> update(@Valid final @RequestBody D dto);

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
    ResponseEntity<D> find(final @PathVariable("id") Long id);

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
    ResponseEntity<List<D>> findAll(final Pageable pageable);

}
