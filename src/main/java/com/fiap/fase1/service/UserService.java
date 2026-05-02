package com.fiap.fase1.service;

import com.fiap.fase1.dto.ChangePasswordDTO;
import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.LoginResponseDTO;
import com.fiap.fase1.dto.UserRequestDTO;
import com.fiap.fase1.dto.UserResponseDTO;
import com.fiap.fase1.dto.UserUpdateDTO;
import com.fiap.fase1.exception.InvalidCredentialsException;
import com.fiap.fase1.exception.EmailAlreadyExistsException;
import com.fiap.fase1.exception.LoginAlreadyExistsException;
import com.fiap.fase1.exception.UserNotFoundException;
import com.fiap.fase1.model.User;
import com.fiap.fase1.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }
        if (repository.existsByLogin(dto.login())) {
            throw new LoginAlreadyExistsException(dto.login());
        }
        User user = new User(
                dto.name(),
                dto.email(),
                dto.login(),
                passwordEncoder.encode(dto.password()),
                dto.address(),
                dto.type()
        );
        return UserResponseDTO.fromEntity(repository.save(user));
    }

    public List<UserResponseDTO> findAll() {
        return findAll(null);
    }

    public List<UserResponseDTO> findAll(String name) {
        List<User> users;

        if (name == null || name.trim().isEmpty()) {
            users = repository.findAll();
        } else {
            users = repository.findByNameContainingIgnoreCase(name.trim());
        }

        return users.stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public List<UserResponseDTO> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public UserResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO update(Long id, UserUpdateDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        repository.findByEmail(dto.email())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new EmailAlreadyExistsException(dto.email()); });

        repository.findByLogin(dto.login())
                .filter(u -> !u.getId().equals(id))
                .ifPresent(u -> { throw new LoginAlreadyExistsException(dto.login()); });

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setLogin(dto.login());
        user.setAddress(dto.address());
        user.setType(dto.type());

        return UserResponseDTO.fromEntity(repository.save(user));
    }

    public void changePassword(Long id, ChangePasswordDTO dto) {
        log.info("Solicitação de troca de senha para o usuário com id: {}", id);
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            log.warn("Troca de senha falhou - senha atual incorreta para o usuário com id: {}", id);
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        repository.save(user);
        log.info("Senha alterada com sucesso para o usuário com id: {}", id);
    }

    public void delete(Long id) {
        log.info("Solicitação de exclusão do usuário com id: {}", id);
        if (!repository.existsById(id)) {
            log.warn("Tentativa de exclusão falhou - usuário não encontrado com id: {}", id);
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
        log.info("Usuário com id: {} excluído com sucesso", id);
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        log.info("Tentativa de login para o usuário: {}", dto.login());

        User user = repository.findByLogin(dto.login())
                .orElse(null);
        if (user == null) {
            log.warn("Tentativa de login falhou - usuário não encontrado: {}", dto.login());
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            log.warn("Tentativa de login falhou - senha incorreta para o usuário: {}", dto.login());
            throw new InvalidCredentialsException();
        }

        log.info("Login realizado com sucesso para o usuário: {}", dto.login());
        return new LoginResponseDTO(
                "Login realizado com sucesso",
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getLastModifiedDate()
        );
    }
}
