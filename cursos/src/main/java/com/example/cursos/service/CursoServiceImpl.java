package com.example.cursos.service;

import com.example.cursos.client.UsuarioClientRest;
import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.model.Usuario;
import com.example.cursos.repository.CursoRepository;
import com.example.cursos.repository.CursoUsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Autowired
    private CursoUsuarioRepository cursoUsuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id) ;
    }

    @Override
    public Curso save(Curso curso) {
        return cursoRepository.save(curso) ;
    }

    @Override
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CursoUsuario addUsuario(Long  usuarioId, Long cursoId) {

        //buscar curso
        Optional<Curso> optionalCurso = cursoRepository.findById(cursoId);

        //validar si existe el cursos
        if(optionalCurso.isPresent()){
            //validar si existe usuario
            Usuario usuario = usuarioClientRest.show(usuarioId);
            System.out.println(usuario);

            //agregar datos
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setCursoId(cursoId);
            cursoUsuario.setUsuarioId(usuarioId);
            cursoUsuarioRepository.save(cursoUsuario);
            return cursoUsuario;
        }

        return null;
    }

    @Override
    @Transactional
    public void removeUsuario(Long usuarioId, Long Id) {
        
    }

    @Override
    public List<Usuario> getUsuarios(Iterable<Long> ids) {
        return  usuarioClientRest.getUsuario(ids) ;
    }

    @Override
    @Transactional
    public List<CursoUsuario> getCursoUsuarios(Long cursoId) {
        return cursoRepository.findByCursoId(cursoId);
    }
}
