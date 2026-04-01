package com.fiap.fase1.service;

import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.UserRequestDTO;
import com.fiap.fase1.dto.UserResponseDTO;
import com.fiap.fase1.exception.InvalidCredentialsException;
import com.fiap.fase1.exception.EmailAlreadyExistsException;
import com.fiap.fase1.exception.LoginAlreadyExistsException;
import com.fiap.fase1.exception.UserNotFoundException;
import com.fiap.fase1.model.User;
import com.fiap.fase1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

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
        User user = new User(dto.name(), dto.email(), dto.login(), passwordEncoder.encode(dto.password()));
        return UserResponseDTO.fromEntity(repository.save(user));
    }

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream().map(UserResponseDTO::fromEntity).toList();
    }

    public UserResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {
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
        user.setPassword(passwordEncoder.encode(dto.password()));

        return UserResponseDTO.fromEntity(repository.save(user));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public UserResponseDTO login(LoginRequestDTO dto) {
        User user = repository.findByLogin(dto.login())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return UserResponseDTO.fromEntity(user);
    }
}
