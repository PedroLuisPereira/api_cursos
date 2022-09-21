package com.example.cursos.repository;

import com.example.cursos.entity.CursoUsuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {

    /**
     * Buscar
     * 
     * @param cedula
     * @param celular
     * @return
     */
    @Query(value = "SELECT l FROM CursoUsuario l WHERE l.cursoId = ?1")
    List<CursoUsuario> findByCursoId(Long cursoId);

    // @Modifying
    // @Query("delete from CursoUsuario c where c.cursoId=:cursoId AND
    // c.usuarioId=:usuarioId")
    // void deleteUsuario(@Param("cursoId") Long cursoId, @Param("usuarioId") Long
    // usuarioId);

    // @Modifying
    // @Query("delete from CursoUsuario c where c.cursoId = ?1 AND c.usuarioId = ?2
    // ")
    // int deleteUsuario(Long cursoId, Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM cursos_usuarios WHERE curso_id = :cursoId AND usuario_id = :usuarioId ", nativeQuery = true)
    int deleteUsuario(@Param("cursoId") Long cursoId, @Param("usuarioId") Long usuarioId);

    @Modifying
    @Query(value = "DELETE FROM cursos_usuarios WHERE usuario_id = :usuarioId ", nativeQuery = true)
    int deleteUsuarioAll(@Param("usuarioId") Long usuarioId);
}
