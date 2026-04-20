package com.fiap.fase1.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/v1/usuarios";

    private Map<String, Object> joao() {
        return Map.of(
                "name", "João Silva",
                "email", "joao.silva@email.com",
                "login", "joaosilva",
                "password", "Senha123",
                "address", "Rua das Flores, 123 - São Paulo/SP",
                "type", "CUSTOMER"
        );
    }

    private Map<String, Object> maria() {
        return Map.of(
                "name", "Maria Oliveira",
                "email", "maria.oliveira@email.com",
                "login", "mariaoliveira",
                "password", "Maria123",
                "address", "Av. Paulista, 1000 - São Paulo/SP",
                "type", "RESTAURANT_OWNER"
        );
    }

    // =========================================================
    // HEALTH CHECK
    // =========================================================

    @Test
    @Order(0)
    @DisplayName("HEALTH - Deve retornar status UP no actuator/health")
    void deveRetornarHealthUp() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.components.db.status").value("UP"));
    }

    // =========================================================
    // ESTADO INICIAL
    // =========================================================

    @Test
    @Order(1)
    @DisplayName("LISTA VAZIA - Deve retornar lista vazia antes de qualquer criação")
    void deveRetornarListaVazia() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // =========================================================
    // CRUD COMPLETO
    // =========================================================

    @Test
    @Order(2)
    @DisplayName("CRUD 1 - Deve criar usuário com sucesso e retornar 201")
    void deveCriarUsuario() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joao())))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"))
                .andExpect(jsonPath("$.login").value("joaosilva"))
                .andExpect(jsonPath("$.type").value("CUSTOMER"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    @Order(3)
    @DisplayName("CRUD 2 - Deve listar usuários e retornar o usuário criado")
    void deveListarUsuarios() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("joao.silva@email.com"));
    }

    @Test
    @Order(4)
    @DisplayName("CRUD 3 - Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorId() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.login").value("joaosilva"));
    }

    @Test
    @Order(5)
    @DisplayName("CRUD 4 - Deve atualizar usuário com sucesso")
    void deveAtualizarUsuario() throws Exception {
        Map<String, Object> update = Map.of(
                "name", "João Silva Atualizado",
                "email", "joao.silva@email.com",
                "login", "joaosilva",
                "address", "Av. Paulista, 1000 - São Paulo/SP",
                "type", "RESTAURANT_OWNER"
        );

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.type").value("RESTAURANT_OWNER"))
                .andExpect(jsonPath("$.address").value("Av. Paulista, 1000 - São Paulo/SP"));
    }

    @Test
    @Order(6)
    @DisplayName("CRUD 5 - Deve persistir dados após atualização (GET após PUT)")
    void devePersistirDadosAposAtualizacao() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva Atualizado"))
                .andExpect(jsonPath("$.type").value("RESTAURANT_OWNER"))
                .andExpect(jsonPath("$.address").value("Av. Paulista, 1000 - São Paulo/SP"));
    }

    @Test
    @Order(7)
    @DisplayName("CRUD 6 - Deve deletar usuário e retornar 204")
    void deveDeletarUsuario() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    @DisplayName("CRUD 7 - Deve confirmar que usuário deletado não existe mais")
    void deveConfirmarDelecao() throws Exception {
        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // =========================================================
    // FLUXO DE LOGIN
    // =========================================================

    @Test
    @Order(10)
    @DisplayName("LOGIN 1 - Deve criar usuário para teste de login")
    void deveCriarUsuarioParaLogin() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joao())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(11)
    @DisplayName("LOGIN 2 - Deve realizar login com credenciais válidas")
    void deveRealizarLoginValido() throws Exception {
        Map<String, String> login = Map.of("login", "joaosilva", "password", "Senha123");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"))
                .andExpect(jsonPath("$.login").value("joaosilva"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"))
                .andExpect(jsonPath("$.userId").isNumber());
    }

    @Test
    @Order(12)
    @DisplayName("LOGIN 3 - Deve retornar 401 com senha incorreta")
    void deveRetornar401SenhaIncorreta() throws Exception {
        Map<String, String> login = Map.of("login", "joaosilva", "password", "SenhaErrada9");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.type").value("https://api.fiap.com/errors/unauthorized"))
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @Order(13)
    @DisplayName("LOGIN 4 - Deve retornar 401 com login inexistente")
    void deveRetornar401LoginInexistente() throws Exception {
        Map<String, String> login = Map.of("login", "naoexiste", "password", "Senha123");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    @Order(14)
    @DisplayName("LOGIN 5 - Deve retornar 400 com campos de login em branco")
    void deveRetornar400CamposLoginEmBranco() throws Exception {
        Map<String, String> login = Map.of("login", "", "password", "");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos").exists());
    }

    // =========================================================
    // TROCA DE SENHA
    // =========================================================

    @Test
    @Order(20)
    @DisplayName("SENHA 1 - Deve trocar senha com sucesso")
    void deveTrocarSenha() throws Exception {
        Map<String, String> dto = Map.of(
                "currentPassword", "Senha123",
                "newPassword", "NovaSenha456"
        );

        mockMvc.perform(patch(BASE_URL + "/2/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Senha alterada com sucesso"));
    }

    @Test
    @Order(21)
    @DisplayName("SENHA 2 - Deve fazer login com a nova senha")
    void deveLoginComNovaSenha() throws Exception {
        Map<String, String> login = Map.of("login", "joaosilva", "password", "NovaSenha456");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login realizado com sucesso"));
    }

    @Test
    @Order(22)
    @DisplayName("SENHA 3 - Deve retornar 400 com senha atual incorreta")
    void deveRetornar400SenhaAtualIncorreta() throws Exception {
        Map<String, String> dto = Map.of(
                "currentPassword", "SenhaErrada9",
                "newPassword", "OutraSenha789"
        );

        mockMvc.perform(patch(BASE_URL + "/2/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    @Order(23)
    @DisplayName("SENHA 4 - Deve retornar 404 ao trocar senha de ID inexistente")
    void deveRetornar404TrocarSenhaIdInexistente() throws Exception {
        Map<String, String> dto = Map.of(
                "currentPassword", "Senha123",
                "newPassword", "NovaSenha456"
        );

        mockMvc.perform(patch(BASE_URL + "/9999/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // =========================================================
    // VALIDAÇÕES DE ENTRADA
    // =========================================================

    @Test
    @Order(30)
    @DisplayName("VALIDAÇÃO 1 - Deve retornar 400 para senha fraca")
    void deveRetornar400SenhaFraca() throws Exception {
        Map<String, Object> dto = Map.of(
                "name", "Teste",
                "email", "teste@email.com",
                "login", "testeteste",
                "password", "fraca",
                "address", "Rua Teste, 1",
                "type", "CUSTOMER"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.password").exists());
    }

    @Test
    @Order(31)
    @DisplayName("VALIDAÇÃO 2 - Deve retornar 400 para HTML no campo name")
    void deveRetornar400HtmlNoNome() throws Exception {
        Map<String, Object> dto = Map.of(
                "name", "<script>alert(1)</script>",
                "email", "xss@email.com",
                "login", "xssuser",
                "password", "Senha123",
                "address", "Rua XSS, 1",
                "type", "CUSTOMER"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.name").exists());
    }

    @Test
    @Order(32)
    @DisplayName("VALIDAÇÃO 3 - Deve retornar 400 para email inválido")
    void deveRetornar400EmailInvalido() throws Exception {
        Map<String, Object> dto = Map.of(
                "name", "Teste",
                "email", "email-invalido",
                "login", "testeteste",
                "password", "Senha123",
                "address", "Rua Teste, 1",
                "type", "CUSTOMER"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.email").exists());
    }

    @Test
    @Order(33)
    @DisplayName("VALIDAÇÃO 4 - Deve retornar 400 para body vazio")
    void deveRetornar400BodyVazio() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos").exists());
    }

    // =========================================================
    // UNICIDADE E CONFLITOS
    // =========================================================

    @Test
    @Order(40)
    @DisplayName("CONFLITO 1 - Deve retornar 409 para email duplicado")
    void deveRetornar409EmailDuplicado() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joao())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.type").value("https://api.fiap.com/errors/conflict"))
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    @Order(41)
    @DisplayName("CONFLITO 2 - Deve retornar 409 para login duplicado")
    void deveRetornar409LoginDuplicado() throws Exception {
        Map<String, Object> dto = Map.of(
                "name", "Outro Usuario",
                "email", "outro@email.com",
                "login", "joaosilva",
                "password", "Senha123",
                "address", "Rua B, 2",
                "type", "CUSTOMER"
        );

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    @Order(42)
    @DisplayName("CONFLITO 3 - Setup: cria segundo usuário para teste de conflito no PUT")
    void deveCriarSegundoUsuario() throws Exception {
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(maria())))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(43)
    @DisplayName("CONFLITO 4 - Deve retornar 409 ao atualizar com email de outro usuário")
    void deveRetornar409AtualizarComEmailDeOutroUsuario() throws Exception {
        Map<String, Object> update = Map.of(
                "name", "Maria Oliveira",
                "email", "joao.silva@email.com",
                "login", "mariaoliveira",
                "address", "Av. Paulista, 1000 - São Paulo/SP",
                "type", "RESTAURANT_OWNER"
        );

        mockMvc.perform(put(BASE_URL + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(44)
    @DisplayName("CONFLITO 5 - Deve retornar 409 ao atualizar com login de outro usuário")
    void deveRetornar409AtualizarComLoginDeOutroUsuario() throws Exception {
        Map<String, Object> update = Map.of(
                "name", "Maria Oliveira",
                "email", "maria.oliveira@email.com",
                "login", "joaosilva",
                "address", "Av. Paulista, 1000 - São Paulo/SP",
                "type", "RESTAURANT_OWNER"
        );

        mockMvc.perform(put(BASE_URL + "/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    // =========================================================
    // NOT FOUND
    // =========================================================

    @Test
    @Order(50)
    @DisplayName("NOT FOUND 1 - Deve retornar 404 para ID inexistente no GET")
    void deveRetornar404IdInexistente() throws Exception {
        mockMvc.perform(get(BASE_URL + "/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.type").value("https://api.fiap.com/errors/not-found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @Order(51)
    @DisplayName("NOT FOUND 2 - Deve retornar 404 para ID inexistente no PUT")
    void deveRetornar404AtualizarIdInexistente() throws Exception {
        Map<String, Object> update = Map.of(
                "name", "Inexistente",
                "email", "x@x.com",
                "login", "xinexistente",
                "address", "Rua X, 1",
                "type", "CUSTOMER"
        );

        mockMvc.perform(put(BASE_URL + "/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    @Order(52)
    @DisplayName("NOT FOUND 3 - Deve retornar 404 para ID inexistente no DELETE")
    void deveRetornar404DeletarIdInexistente() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }
}
