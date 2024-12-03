package com.adriankich.pessoaapi.service;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.domain.exception.NotFoundException;
import com.adriankich.pessoaapi.domain.model.Endereco;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import com.adriankich.pessoaapi.domain.service.EnderecoService;
import com.adriankich.pessoaapi.infrastructure.repository.EnderecoRepository;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest
public class EnderecoServiceTest {

    public static final long ID = 1l;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEnderecoById_ReturnsEndereco() {
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);

        Mockito.when(enderecoRepository.findById(ID)).thenReturn(Optional.of(endereco));

        Endereco result = enderecoService.getEnderecoById(ID);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(Endereco.class, result.getClass());
        Assertions.assertEquals(ID, result.getId());
    }

    @Test
    void testGetEnderecoById_ThrowsNotFoundException() {
        Mockito.when(enderecoRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = Assertions
                .assertThrows(NotFoundException.class, () -> enderecoService.getEnderecoById(ID));
        Assertions.assertEquals("Não foi encontrado um endereço com o id: #" + ID, exception.getMessage());
    }

    @Test
    void testCreateEndereco_ReturnsSuccess() {
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);
        endereco.setPessoa(pessoa);

        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);

        Endereco result = enderecoService.createEndereco(pessoa, enderecoRequestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(endereco.getId(), result.getId());
        Assertions.assertEquals(pessoa.getId(), result.getPessoa().getId());
        Mockito.verify(enderecoRepository, Mockito.times(1)).save(Mockito.any(Endereco.class));
    }

    @Test
    void testUpdateEndereco_ReturnSuccess() {
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);
        endereco.setPessoa(pessoa);

        Mockito.when(enderecoRepository.findById(ID)).thenReturn(Optional.of(endereco));
        Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);

        Endereco result = enderecoService.updateEndereco(ID, enderecoRequestDTO);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), endereco.getId());
        Assertions.assertEquals(Endereco.class, result.getClass());
        Assertions.assertEquals(result.getPessoa(), pessoa);
        Mockito.verify(enderecoRepository, Mockito.times(1)).save(Mockito.any(Endereco.class));
    }

    @Test
    void testUpdateEndereco_ThrowsNotFoundException() {
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();
        Pessoa pessoa = PessoaStub.createPessoaWithId(ID);

        Mockito.when(enderecoRepository.findById(ID)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> enderecoService.updateEndereco(pessoa.getId(), enderecoRequestDTO));
        Assertions.assertEquals("Não foi encontrado um endereço com o id: #" + pessoa.getId(), exception.getMessage());
    }

    @Test
    void testDeleteEndereco_ReturnsSuccess() {
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);

        Mockito.when(enderecoRepository.findById(ID)).thenReturn(Optional.of(endereco));

        enderecoService.deleteEndereco(ID);

        Mockito.verify(enderecoRepository, Mockito.times(1)).deleteById(ID);
    }

    @Test
    void testDeleteEndereco_ThrowsNotFoundException() {
        Endereco endereco = EnderecoStub.createEnderecoWithId(ID);

        Mockito.when(enderecoRepository.findById(ID)).thenReturn(Optional.empty());

        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> enderecoService.deleteEndereco(ID));
        Assertions.assertEquals("Não foi encontrado um endereço com o id: #" + ID, exception.getMessage());
    }
}
