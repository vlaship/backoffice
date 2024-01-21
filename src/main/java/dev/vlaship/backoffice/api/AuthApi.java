package dev.vlaship.backoffice.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import dev.vlaship.backoffice.dto.LoginRequest;
import dev.vlaship.backoffice.dto.LoginResponse;
import dev.vlaship.backoffice.dto.SignupRequest;

@Validated
@Tag(name = "auth")
@RequestMapping("/api/auth")
public interface AuthApi {

    @Operation(
            operationId = "signup",
            summary = "sign up",
            tags = { "auth" },
            responses = {
                    @ApiResponse(responseCode = "202", description = "Accepted"),
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
    @PostMapping(value = "/signup")
    ResponseEntity<Void> signup(@RequestBody @Valid SignupRequest request);

    @Operation(
            operationId = "login",
            summary = "login",
            tags = { "auth" },
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
    @PostMapping(value = "/login")
    ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request);

}
