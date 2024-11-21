package com.adriankich.pessoaapi.infrastructure.repository;

import com.adriankich.pessoaapi.domain.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
