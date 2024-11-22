package com.adriankich.pessoaapi.controller;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.PessoaRequestDTO;
import com.adriankich.pessoaapi.application.dto.request.UpdatePessoaDTO;
import com.adriankich.pessoaapi.helpers.QueryProvider;
import com.adriankich.pessoaapi.model.EnderecoStub;
import com.adriankich.pessoaapi.model.PessoaStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.adriankich.pessoaapi.helpers.Serializer.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/pessoas";

    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    void testCreatePessoa_ReturnSuccess201() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = PessoaStub.createPessoaRequestDTO();

        mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(pessoaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(pessoaRequestDTO.nome()))
                .andExpect(jsonPath("$.cpf").value(pessoaRequestDTO.cpf()))
                .andExpect(jsonPath("$.dtNascimento").value(FORMATTER.format(pessoaRequestDTO.dtNascimento())))
                .andExpect(jsonPath("$.enderecos[0].rua").value(pessoaRequestDTO.enderecos().get(0).rua()))
                .andExpect(jsonPath("$.enderecos[0].numero").value(pessoaRequestDTO.enderecos().get(0).numero()))
                .andExpect(jsonPath("$.enderecos[0].bairro").value(pessoaRequestDTO.enderecos().get(0).bairro()))
                .andExpect(jsonPath("$.enderecos[0].cidade").value(pessoaRequestDTO.enderecos().get(0).cidade()))
                .andExpect(jsonPath("$.enderecos[0].estado").value(pessoaRequestDTO.enderecos().get(0).estado()))
                .andExpect(jsonPath("$.enderecos[0].cep").value(pessoaRequestDTO.enderecos().get(0).cep()));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testCreatePessoa_ReturnBadRequest400DuplicateCpf() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = PessoaStub.createPessoaRequestDTO();

        mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(pessoaRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Já existe uma pessoa cadastrada com esse CPF."));
    }

    @Test
    void testCreatePessoa_ReturnBadRequest400WithoutRequiredFields() throws Exception {
        PessoaRequestDTO pessoaRequestDTO = new PessoaRequestDTO("", "", LocalDate.now(), new ArrayList<>());

        mockMvc.perform(post(PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(pessoaRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro na validação dos campos"));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testGetPessoaById_ReturnSuccess200() throws Exception {
        mockMvc.perform(get(PATH + "/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetPessoaById_ReturnNotFound404() throws Exception {
        mockMvc.perform(get(PATH + "/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Não foi encontrado uma pessoa com o id: #" + 1));
    }

    @Test
    void testGetAllPessoas_ReturnSuccess200() throws Exception {
        mockMvc.perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testUpdatePessoa_ReturnSuccess200() throws Exception {
        UpdatePessoaDTO updatePessoaDTO = PessoaStub.createUpdatePessoaDTO();

        mockMvc.perform(put(PATH+ "/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(updatePessoaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value(updatePessoaDTO.nome()));
    }

    @Test
    void testUpdatePessoa_ReturnNotFound404() throws Exception {
        UpdatePessoaDTO updatePessoaDTO = PessoaStub.createUpdatePessoaDTO();

        mockMvc.perform(put(PATH+ "/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(updatePessoaDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Não foi encontrado uma pessoa com o id: #" + 1));
    }

    @Test
    void testUpdatePessoa_ReturnBadRequest400WithoutRequiredFields() throws Exception {
        UpdatePessoaDTO updatePessoaDTO = new UpdatePessoaDTO("", "", LocalDate.now());

        mockMvc.perform(post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json(updatePessoaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro na validação dos campos"));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testDeletePessoa_ReturnNoContent204() throws Exception {
        mockMvc.perform(delete(PATH+ "/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeletePessoa_ReturnNotFound404() throws Exception {
        mockMvc.perform(delete(PATH+ "/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Não foi encontrado uma pessoa com o id: #" + 1));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testAddEndereco_ReturnSuccess201() throws Exception {
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();

        mockMvc.perform(post(PATH+ "/{id}/enderecos", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(enderecoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.enderecos").isArray())
                .andExpect(jsonPath("$.enderecos").isNotEmpty());
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testAddEndereco_ReturnBadRequest400WithoutRequiredFields() throws Exception {
        EnderecoRequestDTO enderecoRequestDTO =
                new EnderecoRequestDTO("", "", "", "", "", "", 1 );

        mockMvc.perform(post(PATH+ "/{id}/enderecos", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(enderecoRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro na validação dos campos"));
    }
}
