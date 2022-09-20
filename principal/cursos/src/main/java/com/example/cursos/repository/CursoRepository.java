package com.example.cursos.repository;

import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CursoRepository extends JpaRepository<Curso, Long> {

     /**
     * Buscar 
     * @param cedula
     * @param celular
     * @return
     */
    @Query(value = "SELECT l FROM CursoUsuario l WHERE l.cursoId = ?1")
    List<CursoUsuario> findByCursoId(Long cursoId);
}


