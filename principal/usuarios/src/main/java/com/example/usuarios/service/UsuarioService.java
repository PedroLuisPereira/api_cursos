package com.example.usuarios.service;

import com.example.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> findAll();

    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario);

    void delete(Long id);

    List<Usuario> findAllByIds(Iterable<Long> ids);

}
