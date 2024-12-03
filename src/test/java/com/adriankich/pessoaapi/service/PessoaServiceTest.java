package com.adriankich.pessoaapi.service;

import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.domain.exception.CpfAlreadyUsedException;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import com.adriankich.pessoaapi.domain.service.EmailService;
import com.adriankich.pessoaapi.domain.service.EnderecoService;
import com.adriankich.pessoaapi.domain.service.PessoaService;
import com.adriankich.pessoaapi.infrastructure.repository.PessoaRepository;
import com.adriankich.pessoaapi.model.EnderecoStub;
import com.adriankich.pessoaapi.model.PessoaStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class PessoaServiceTest {

    public static final long ID = 1l;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private EmailService emailService;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPessoas_ReturnsListOfPessoas() {
        Pessoa pessoa = PessoaStub.createPessoaWithoutId();
        Pageable pageable = PageRequest.of(0, 1);

        Page<Pessoa> page = new PageImpl<>(List.of(pessoa, pessoa), pageable, 2);

        Mockito.when(pessoaRepository.findAll(pageable)).thenReturn(page);

        Page<Pessoa> result = pessoaService.getAllPessoas(pageable);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTotalElements());
        Assertions.assertEquals(2, result.getTotalPages());
        Assertions.assertEquals(Pessoa.class, result.getContent().get(0).getClass());
    }

    @Test
    void testGetPessoaById_ReturnsPessoa() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.getPessoaById(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Pessoa.class, result.getClass());
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void testGetPessoaById_ThrowsNotFoundException() {
        Mockito.when(pessoaRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = Assertions
                .assertThrows(NotFoundException.class, () -> pessoaService.getPessoaById(ID));
        Assertions.assertEquals("Não foi encontrado uma pessoa com o id: #" + ID, exception.getMessage());
    }

    @Test
    void testCreatePessoa_ReturnsSuccess() {
        PessoaRequestDTO pessoaRequestDTO = PessoaStub.createPessoaRequestDTO();
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);

        Mockito.when(enderecoService.createEndereco(Mockito.any(), Mockito.any())).thenReturn(endereco);
        Mockito.when(pessoaRepository.findByCpf(pessoaRequestDTO.cpf())).thenReturn(Optional.empty());
        Mockito.when(pessoaRepository.save(Mockito.any(Pessoa.class))).thenReturn(pessoa);

        Pessoa result = pessoaService.createPessoa(pessoaRequestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Pessoa.class, result.getClass());
        Assertions.assertEquals(pessoaRequestDTO.cpf(), result.getCpf());
        Mockito.verify(pessoaRepository, Mockito.times(1)).save(Mockito.any(Pessoa.class));
    }

    @Test
    void testCreatePessoa_ThrowsCpfAlreadyUsedException() {
        PessoaRequestDTO pessoaRequestDTO = PessoaStub.createPessoaRequestDTO();
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);

        Mockito.when(pessoaRepository.findByCpf(pessoaRequestDTO.cpf())).thenReturn(Optional.of(pessoa));

        CpfAlreadyUsedException exception = Assertions.assertThrows(CpfAlreadyUsedException.class,
                () -> pessoaService.createPessoa(pessoaRequestDTO));
        Assertions.assertEquals("Já existe uma pessoa cadastrada com esse CPF.", exception.getMessage());
    }

    @Test
    void testUpdatePessoa_ReturnsSuccess() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        UpdatePessoaDTO updatePessoaDTO = PessoaStub.createUpdatePessoaDTO();

        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa));
        Mockito.when(pessoaRepository.save(pessoa)).thenReturn(pessoa);
        Mockito.when(pessoaRepository.findByCpf(updatePessoaDTO.cpf())).thenReturn(Optional.of(pessoa));

        Pessoa result = pessoaService.updatePessoa(ID, updatePessoaDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getNome(), updatePessoaDTO.nome());
        Assertions.assertEquals(result.getId(), pessoa.getId());

        Mockito.verify(pessoaRepository).save(pessoa);
    }

    @Test
    void testUpdatePessoa_ThrowsCpfAlreadyUsedException() {
        Pessoa pessoa1 = PessoaStub.createPessoaWithId(ID);
        Pessoa pessoa2 = PessoaStub.createPessoaWithId(ID + 1);
        UpdatePessoaDTO updatePessoaDTO = PessoaStub.createUpdatePessoaDTO();

        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa1));
        Mockito.when(pessoaRepository.findByCpf(updatePessoaDTO.cpf())).thenReturn(Optional.of(pessoa2));

        CpfAlreadyUsedException exception = Assertions.assertThrows(CpfAlreadyUsedException.class,
                () -> pessoaService.updatePessoa(ID, updatePessoaDTO));
        Assertions.assertEquals("Já existe uma pessoa cadastrada com esse CPF.", exception.getMessage());
    }

    @Test
    void testUpdatePessoa_ThrowsNotFoundException() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        UpdatePessoaDTO updatePessoaDTO = PessoaStub.createUpdatePessoaDTO();

        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> pessoaService.updatePessoa(ID, updatePessoaDTO));
        Assertions.assertEquals("Não foi encontrado uma pessoa com o id: #" + ID, exception.getMessage());
    }

    @Test
    void testDeletePessoa_ReturnsSuccess() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);

        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.of(pessoa));

        pessoaService.deletePessoa(ID);

        Mockito.verify(pessoaRepository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void testDeletePessoa_ThrowsNotFoundException() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);

        Mockito.when(pessoaRepository.findById(ID)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> pessoaService.deletePessoa(ID));
        Assertions.assertEquals("Não foi encontrado uma pessoa com o id: #" + ID, exception.getMessage());
    }
}
