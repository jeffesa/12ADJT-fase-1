package com.fiap.fase1.service;

import com.fiap.fase1.dto.LoginRequestDTO;
import com.fiap.fase1.dto.UsuarioRequestDTO;
import com.fiap.fase1.dto.UsuarioResponseDTO;
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
        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailJaCadastradoException(dto.getEmail());
        }
        Usuario usuario = new Usuario(dto.getNome(), dto.getEmail(), passwordEncoder.encode(dto.getSenha()));
        return new UsuarioResponseDTO(repository.save(usuario));
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(UsuarioResponseDTO::new).toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        return new UsuarioResponseDTO(repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id)));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        return new UsuarioResponseDTO(repository.save(usuario));
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) throw new UsuarioNaoEncontradoException(id);
        repository.deleteById(id);
    }

    public UsuarioResponseDTO login(LoginRequestDTO dto) {
        Usuario usuario = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email ou senha inválidos"));
        if (!passwordEncoder.matches(dto.getSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }
        return new UsuarioResponseDTO(usuario);
    }
}
