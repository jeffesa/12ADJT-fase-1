package com.fiap.fase1.controller;

import com.fiap.fase1.dto.ChangePasswordDTO;
import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.LoginResponseDTO;
import com.fiap.fase1.dto.UserRequestDTO;
import com.fiap.fase1.dto.UserResponseDTO;
import com.fiap.fase1.dto.UserUpdateDTO;
import com.fiap.fase1.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Operações de criação, consulta, atualização e exclusão de usuários")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar usuário",
            description = "Cadastra um novo usuário no sistema. Login e email devem ser únicos. A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, minúscula e número."
    )
    @RequestBody(
            required = true,
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserRequestDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Cliente",
                                    summary = "Cadastro de cliente",
                                    value = """
                        {
                          "name": "João Silva",
                          "email": "joao.silva@email.com",
                          "login": "joaosilva",
                          "password": "Senha123",
                          "address": "Rua das Flores, 123 - São Paulo/SP",
                          "type": "CUSTOMER"
                        }
                        """
                            ),
                            @ExampleObject(
                                    name = "Dono de Restaurante",
                                    summary = "Cadastro de dono de restaurante",
                                    value = """
                        {
                          "name": "Maria Oliveira",
                          "email": "maria.oliveira@restaurante.com",
                          "login": "mariaoliveira",
                          "password": "Restaurante1",
                          "address": "Av. Paulista, 1000 - São Paulo/SP",
                          "type": "RESTAURANT_OWNER"
                        }
                        """
                            )
                    }
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ou login já cadastrado")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @Valid @org.springframework.web.bind.annotation.RequestBody UserRequestDTO dto) {

        UserResponseDTO response = service.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @Operation(
            summary = "Listar usuários ou buscar por nome",
            description = "Retorna todos os usuários cadastrados. Se o parâmetro 'name' for informado, realiza busca parcial por nome sem diferenciar maiúsculas e minúsculas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllOrSearchByName(
            @Parameter(
                    description = "Nome para busca parcial",
                    example = "João"
            )
            @RequestParam(required = false) String name) {

        if (name != null && !name.isBlank()) {
            return ResponseEntity.ok(service.findByName(name));
        }

        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário existente. Não altera senha."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ou login já cadastrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody UserUpdateDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(
            summary = "Deletar usuário",
            description = "Remove permanentemente um usuário pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do usuário", example = "1", required = true)
            @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Trocar senha",
            description = "Altera a senha do usuário.",
            tags = {"Autenticação"}
    )
    @PatchMapping("/{id}/password")
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable Long id,
            @Valid @org.springframework.web.bind.annotation.RequestBody ChangePasswordDTO dto) {

        service.changePassword(id, dto);
        return ResponseEntity.ok(Map.of("mensagem", "Senha alterada com sucesso"));
    }

    @Operation(
            summary = "Login",
            description = "Valida login e senha.",
            tags = {"Autenticação"}
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO dto) {

        return ResponseEntity.ok(service.login(dto));
    }
}