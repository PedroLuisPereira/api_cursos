package com.example.cursos.repository;

import com.example.cursos.entity.CursoUsuario;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {
   
}


