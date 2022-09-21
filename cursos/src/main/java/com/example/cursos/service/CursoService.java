package com.example.cursos.service;

import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);

    void delete(Long id);

    // //agregar un usuario a un curso
    // //CursoUsuario addUsuario(Long usuarioId, Long Id);

    // //void removeUsuario(Long usuarioId, Long Id);

    

    // Usuario getUsuario(Long id);

    // List<Usuario> getUsuarios(Iterable<Long> ids);

}
