package com.example.cursos.service;

import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.repository.CursoUsuarioRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoUsuarioServiceImpl implements CursoUsuarioService {

    @Autowired
    private CursoUsuarioRepository cursoUsuarioRepository;

    @Override
    @Transactional
    public CursoUsuario addUsuario(CursoUsuario cursoUsuario) {
        return cursoUsuarioRepository.save(cursoUsuario);
    }

    @Override
    public void removeUsuario(Long cursoId, Long usuarioId) {
        int filas = cursoUsuarioRepository.deleteUsuario(cursoId, usuarioId);
    }

    @Override
    public List<CursoUsuario> getUsuarios(Long cursoId) {
        return cursoUsuarioRepository.findByCursoId(cursoId) ;
    }

}
