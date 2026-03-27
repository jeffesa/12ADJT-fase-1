package com.fiap.fase1.repository;

import com.fiap.fase1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    Optional<Usuario> findByLogin(String login);
    
    boolean existsByLogin(String login);
    
    Optional<Usuario> findByLoginAndSenha(String login, String senha);
}
