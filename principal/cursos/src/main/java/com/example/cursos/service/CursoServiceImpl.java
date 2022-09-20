package com.example.cursos.service;

import com.example.cursos.client.UsuarioClientRest;
import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.model.Usuario;
import com.example.cursos.repository.CursoRepository;
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
    public Usuario addUsuario(Usuario usuario, Long id) {

        //buscar curso
        Optional<Curso> optionalCurso = cursoRepository.findById(id);

        //validar si existe el cursos
        if(optionalCurso.isPresent()){
            Usuario usuario1 = usuarioClientRest.show(usuario.getId());
            Curso curso = optionalCurso.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuario1.getId());
            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return usuario1;
        }

        return null;
    }

    @Override
    @Transactional
    public Usuario removeUsuario(Usuario usuario, Long Id) {
        return null;
    }
}
