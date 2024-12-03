package com.adriankich.pessoaapi.controller;

import com.adriankich.pessoaapi.application.dto.request.EnderecoRequestDTO;
import com.adriankich.pessoaapi.helpers.QueryProvider;
import com.adriankich.pessoaapi.model.EnderecoStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static com.adriankich.pessoaapi.helpers.Serializer.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/enderecos";

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertEndereco),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testUpdateEndereco_ReturnSuccess200() throws Exception {
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();

        mockMvc.perform(put(PATH + "/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(enderecoRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rua").value(enderecoRequestDTO.rua()))
                .andExpect(jsonPath("$.numero").value(enderecoRequestDTO.numero()))
                .andExpect(jsonPath("$.bairro").value(enderecoRequestDTO.bairro()))
                .andExpect(jsonPath("$.cidade").value(enderecoRequestDTO.cidade()))
                .andExpect(jsonPath("$.estado").value(enderecoRequestDTO.estado()))
                .andExpect(jsonPath("$.cep").value(enderecoRequestDTO.cep()));
    }

    @Test
    void testUpdateEndereco_ReturnNotFound404() throws Exception {
        EnderecoRequestDTO enderecoRequestDTO = EnderecoStub.createEnderecoRequestDTO();

        mockMvc.perform(put(PATH + "/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(enderecoRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Não foi encontrado um endereço com o id: #" + 1));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertEndereco),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testUpdateEndereco_ReturnBadRequest400WithoutRequiredFields() throws Exception{
        EnderecoRequestDTO enderecoRequestDTO =
                new EnderecoRequestDTO("", "", "", "", "", "", 1 );

        mockMvc.perform(put(PATH + "/{id}", 1)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json(enderecoRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Erro na validação dos campos"));
    }

    @Test
    @SqlGroup({
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertPessoa),
            @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = QueryProvider.insertEndereco),
            @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = QueryProvider.resetDB)
    })
    void testDeleteEndereco_ReturnNoContent204() throws Exception {
        mockMvc.perform(delete(PATH+ "/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEndereco_ReturnNotFound404() throws Exception {
        mockMvc.perform(delete(PATH+ "/{id}", 1))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Não foi encontrado um endereço com o id: #" + 1));
    }
}
