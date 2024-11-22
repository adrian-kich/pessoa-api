package com.adriankich.pessoaapi.application.swagger;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.application.dto.response.PessoaResponseDTO;
import com.adriankich.pessoaapi.application.exception.StandardError;
import com.adriankich.pessoaapi.domain.model.Pessoa;
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

import java.util.List;

public interface PessoaSwagger {

    final String TAG_NAME = "Pessoa";

    @Operation(
        operationId = "buscar pessoas",
        summary = "Busca todos as pessoas com seus endereços",
        tags = { TAG_NAME },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = SwaggerConfig.SUCCESS_MESSAGE,
                        content = @Content(
                                schema = @Schema( implementation = PessoaResponseDTO.class ),
                                mediaType = MediaType.APPLICATION_JSON_VALUE
                        )
                )
        }
    )
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas();

    @Operation(
            operationId = "buscar pessoa",
            summary = "Busca pessoa de acordo com seu id",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = PessoaResponseDTO.class ),
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
            }
    )
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id);

    @Operation(
            operationId = "Registra pessoa",
            summary = "Registra pessoa e seus endereços",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = PessoaResponseDTO.class ),
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
                    )
            }
    )
    public ResponseEntity<PessoaResponseDTO> createPessoa(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO);

    @Operation(
            operationId = "Atualiza pessoa",
            summary = "Atualiza dados básicos da pessoa",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = PessoaResponseDTO.class ),
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
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long id,
                                                          @RequestBody @Valid UpdatePessoaDTO updatePessoaDTO);

    @Operation(
            operationId = "Adiciona endereco",
            summary = "Adiciona um endereço a uma pessoa",
            tags = { TAG_NAME },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = SwaggerConfig.SUCCESS_MESSAGE,
                            content = @Content(
                                    schema = @Schema( implementation = PessoaResponseDTO.class ),
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
    public ResponseEntity<PessoaResponseDTO> addEndereco(@PathVariable Long id,
                                                         @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO);

    @Operation(
            operationId = "Deleta pessoa",
            summary = "Exclui uma pessoa e seus endereços",
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
    public ResponseEntity<?> deletePessoa(@PathVariable Long id);
}
