package com.adriankich.pessoaapi.domain.service;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.domain.enums.EnderecoType;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import com.adriankich.pessoaapi.infrastructure.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco getEnderecoById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado um endereço com o id: #" + id));
    }

    public Endereco addEndereco(Endereco endereco) {
        enderecoRepository.save(endereco);
        return endereco;
    }

    public Endereco createEndereco(Pessoa pessoa, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = Endereco.builder()
                .pessoa(pessoa)
                .rua(enderecoRequestDTO.rua())
                .numero(enderecoRequestDTO.numero())
                .bairro(enderecoRequestDTO.bairro())
                .cidade(enderecoRequestDTO.cidade())
                .estado(enderecoRequestDTO.estado())
                .cep(enderecoRequestDTO.cep())
                .type(EnderecoType.fromValue(enderecoRequestDTO.type()) == null
                        ? EnderecoType.PRINCIPAL : EnderecoType.fromValue(enderecoRequestDTO.type()))
                .build();

        return addEndereco(endereco);
    }

    public Endereco updateEndereco(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = getEnderecoById(id);

        endereco.setRua(enderecoRequestDTO.rua());
        endereco.setNumero(enderecoRequestDTO.numero());
        endereco.setBairro(enderecoRequestDTO.bairro());
        endereco.setCidade(enderecoRequestDTO.cidade());
        endereco.setEstado(enderecoRequestDTO.estado());
        endereco.setCep(enderecoRequestDTO.cep());
        endereco.setType(EnderecoType.fromValue(enderecoRequestDTO.type()));

        enderecoRepository.save(endereco);
        return endereco;
    }

    public void deleteEndereco(Long id) {
        getEnderecoById(id);
        enderecoRepository.deleteById(id);
    }

    public void checkType() {

    }
}
