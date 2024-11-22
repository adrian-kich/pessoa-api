package com.adriankich.pessoaapi.infrastructure.repository;

import com.adriankich.pessoaapi.domain.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PagingAndSortingRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
}
