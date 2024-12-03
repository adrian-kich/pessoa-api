package com.adriankich.pessoaapi.application.swagger;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.response.EnderecoResponseDTO;
import com.adriankich.pessoaapi.application.dto.response.PessoaResponseDTO;
import com.adriankich.pessoaapi.application.exception.StandardError;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.infrastructure.config.SwaggerConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface EnderecoSwagger {

    final String TAG_NAME = "Endereco";

    @Operation(
            operationId = "Atualiza endereço",
            summary = "Atualiza dados do endereço informado",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = EnderecoResponseDTO.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = SwaggerConfig.NOT_FOUND_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = StandardError.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = SwaggerConfig.BAD_REQUEST_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = StandardError.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    public ResponseEntity<EnderecoResponseDTO> updateEndereco(@PathVariable Long id,
                                                              @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO);

    @Operation(
            operationId = "Deleta endereço",
            summary = "Exclui o endereço informado",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = SwaggerConfig.SUCCESS_MESSAGE
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = SwaggerConfig.NOT_FOUND_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = StandardError.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    public ResponseEntity<?> deleteEndereco(@PathVariable Long id);
}
