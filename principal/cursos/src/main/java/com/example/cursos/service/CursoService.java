package com.example.cursos.service;

import com.example.cursos.entity.Curso;
import com.example.cursos.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void delete(Long id);

    Usuario addUsuario(Usuario usuario, Long Id);
    Usuario removeUsuario(Usuario usuario, Long Id);

}
