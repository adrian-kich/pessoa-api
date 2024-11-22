package com.adriankich.pessoaapi.domain.service;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.domain.exception.CpfAlreadyUsedException;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import com.adriankich.pessoaapi.infrastructure.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private EnderecoService enderecoService;

    public Pessoa getPessoaById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não foi encontrado uma pessoa com o id: #" + id));
    }

    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa createPessoa(PessoaRequestDTO pessoaRequestDTO) {
        String cpf = formatCpf(pessoaRequestDTO.cpf());

        if(pessoaRepository.findByCpf(cpf).isPresent())
            throw new CpfAlreadyUsedException("Já existe uma pessoa cadastrada com esse CPF.");

        Pessoa pessoa = Pessoa.builder()
                .nome(pessoaRequestDTO.nome())
                .cpf(cpf)
                .dtNascimento(pessoaRequestDTO.dtNascimento())
                .build();

        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        pessoa.setEnderecos(pessoaRequestDTO.enderecos().stream()
                .map(enderecoRequestDTO -> enderecoService.createEndereco(pessoa, enderecoRequestDTO))
                .toList());

        return savedPessoa;
    };

    public Pessoa updatePessoa(Long id, UpdatePessoaDTO updatePessoaDTO) {
        Pessoa pessoa = getPessoaById(id);

        String cpf = formatCpf(updatePessoaDTO.cpf());

        Optional<Pessoa> checkCpf = pessoaRepository.findByCpf(cpf);
        if(checkCpf.isPresent() && !checkCpf.get().getId().equals(id))
            throw new CpfAlreadyUsedException("Já existe uma pessoa cadastrada com esse CPF.");

        pessoa.setNome(updatePessoaDTO.nome());
        pessoa.setCpf(cpf);
        pessoa.setDtNascimento(updatePessoaDTO.dtNascimento());

        return pessoaRepository.save(pessoa);
    }

    public void deletePessoa(Long id) {
        getPessoaById(id);
        pessoaRepository.deleteById(id);
    }

    public Pessoa addEndereco(Long pessoaId, EnderecoRequestDTO enderecoRequestDTO) {
        Pessoa pessoa = getPessoaById(pessoaId);

        enderecoService.createEndereco(pessoa, enderecoRequestDTO);
        return pessoa;
    }

    public String formatCpf(String cpf) {
        return cpf.replaceAll("\\.|-", "");
    }
}
