package com.adriankich.pessoaapi.application.controllers;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.application.dto.response.PessoaResponseDTO;
import com.adriankich.pessoaapi.application.swagger.PessoaSwagger;
import com.adriankich.pessoaapi.domain.mapper.PessoaMapper;
import com.adriankich.pessoaapi.domain.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
public class PessoaController implements PessoaSwagger {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> getAllPessoas() {
        return ResponseEntity.ok(pessoaService.getAllPessoas().stream()
                .map(PessoaMapper::entityToDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> getPessoaById(@PathVariable Long id) {
        return ResponseEntity.ok(PessoaMapper.entityToDto(pessoaService.getPessoaById(id)));
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> createPessoa(@RequestBody @Valid PessoaRequestDTO pessoaRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PessoaMapper.entityToDto(pessoaService.createPessoa(pessoaRequestDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> updatePessoa(@PathVariable Long id,
                                                          @RequestBody @Valid UpdatePessoaDTO updatePessoaDTO) {
        return ResponseEntity.ok(PessoaMapper.entityToDto(pessoaService.updatePessoa(id, updatePessoaDTO)));
    }

    @PostMapping("/{id}/enderecos")
    public ResponseEntity<PessoaResponseDTO> addEndereco(@PathVariable Long id,
                                                         @RequestBody @Valid EnderecoRequestDTO enderecoRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(PessoaMapper.entityToDto(
                pessoaService.addEndereco(id, enderecoRequestDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }
}
