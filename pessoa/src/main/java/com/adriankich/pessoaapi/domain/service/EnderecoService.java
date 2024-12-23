package com.adriankich.pessoaapi.domain.service;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.domain.enums.EnderecoType;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import com.adriankich.pessoaapi.infrastructure.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco getEnderecoById(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado um endereço com o id: #" + id));
    }

    public Endereco addEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
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
                        ? checkType(pessoa.getId())
                        : EnderecoType.fromValue(enderecoRequestDTO.type()) == EnderecoType.SECUNDARIO
                        ? checkType(pessoa.getId())
                        : setPrincipalType(pessoa.getId()))
                .build();

        return addEndereco(endereco);
    }

    public Endereco updateEndereco(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = getEnderecoById(id);

        Pessoa pessoa = endereco.getPessoa();

        endereco.setRua(enderecoRequestDTO.rua());
        endereco.setNumero(enderecoRequestDTO.numero());
        endereco.setBairro(enderecoRequestDTO.bairro());
        endereco.setCidade(enderecoRequestDTO.cidade());
        endereco.setEstado(enderecoRequestDTO.estado());
        endereco.setCep(enderecoRequestDTO.cep());
        endereco.setType(EnderecoType.fromValue(enderecoRequestDTO.type()) == null
                ? checkUpdateType(pessoa.getId(), id)
                : EnderecoType.fromValue(enderecoRequestDTO.type()) == EnderecoType.SECUNDARIO
                ? checkUpdateType(pessoa.getId(), id)
                : setPrincipalType(pessoa.getId()));

        return enderecoRepository.save(endereco);
    }

    public void deleteEndereco(Long id) {
        getEnderecoById(id);
        enderecoRepository.deleteById(id);
    }

    public EnderecoType checkType(Long pessoaId) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);

        if(!enderecos.isEmpty()) {
            if(enderecos.stream()
                    .anyMatch(endereco -> endereco.getType().getValue() == EnderecoType.PRINCIPAL.getValue())) {
                return EnderecoType.SECUNDARIO;
            }
        }
        return EnderecoType.PRINCIPAL;
    }

    public EnderecoType checkUpdateType(Long pessoaId, Long id) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);

        if(!enderecos.isEmpty()) {
            if(enderecos.stream()
                    .anyMatch(endereco -> endereco.getType().getValue() == EnderecoType.PRINCIPAL.getValue()
                            && !Objects.equals(endereco.getId(), id))) {
                return EnderecoType.SECUNDARIO;
            }
        }
        return EnderecoType.PRINCIPAL;
    }

    public EnderecoType setPrincipalType(Long pessoaId) {
        List<Endereco> enderecos = enderecoRepository.findByPessoaId(pessoaId);

        if(!enderecos.isEmpty()) {
            enderecos.forEach(endereco -> {
                endereco.setType(EnderecoType.SECUNDARIO);
                enderecoRepository.save(endereco);
            });
        }
        return EnderecoType.PRINCIPAL;
    }
}
