package com.example.cursos.service;

import java.util.List;

import com.example.cursos.entity.CursoUsuario;


public interface CursoUsuarioService {


    //listar los usuarios de un curso
    List<CursoUsuario> getUsuarios(Long cursoId);

    //agregar un usuario a un curso
    CursoUsuario addUsuario(CursoUsuario cursoUsuario);

    //remover un usuairo de un curso
    void removeUsuario(Long cursoId, Long usuarioId);

}
