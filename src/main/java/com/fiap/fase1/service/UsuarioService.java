package com.fiap.fase1.service;

import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.UsuarioRequestDTO;
import com.fiap.fase1.dto.UsuarioResponseDTO;
import com.fiap.fase1.exception.CredenciaisInvalidasException;
import com.fiap.fase1.exception.EmailJaCadastradoException;
import com.fiap.fase1.exception.UsuarioNaoEncontradoException;
import com.fiap.fase1.model.Usuario;
import com.fiap.fase1.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }
        Usuario usuario = new Usuario(dto.nome(), dto.email(), dto.login(), passwordEncoder.encode(dto.senha()));
        return UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(UsuarioResponseDTO::fromEntity).toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return repository.findById(id)
                .map(UsuarioResponseDTO::fromEntity)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setLogin(dto.login());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));

        return UsuarioResponseDTO.fromEntity(repository.save(usuario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    public UsuarioResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = repository.findByLogin(dto.login())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        return UsuarioResponseDTO.fromEntity(usuario);
    }
}
