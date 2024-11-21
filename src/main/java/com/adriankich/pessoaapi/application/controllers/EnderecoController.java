package com.adriankich.pessoaapi.application.controllers;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.response.EnderecoResponseDTO;
import com.adriankich.pessoaapi.domain.mapper.EnderecoMapper;
import com.adriankich.pessoaapi.domain.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/enderecos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponseDTO> updateEndereco(@PathVariable Long id,
                                                              @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.ok(EnderecoMapper.entityToDto(enderecoService.updateEndereco(id, enderecoRequestDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEndereco(@PathVariable Long id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.noContent().build();
    }
}
