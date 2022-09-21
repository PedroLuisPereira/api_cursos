package com.example.cursos.controller;

import com.example.cursos.client.UsuarioClientRest;
import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.model.Usuario;
import com.example.cursos.service.CursoService;
import com.example.cursos.service.CursoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Autowired
    private CursoUsuarioService cursoUsuarioService;

    /**
     * Listar todos los cursos
     * 
     * @return
     */
    @GetMapping("/")
    public List<Curso> index() {
        return cursoService.findAll();
    }

    /**
     * listar un curso con sus usuarios
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        // verficar si el curso existe
        Optional<Curso> byOptional = cursoService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // buscar los usuarios del curso
        List<CursoUsuario> cursoUsuarios = cursoUsuarioService.getUsuarios(id);

        // obtener los id de los usuarios
        ArrayList<Long> ids = new ArrayList<Long>();
        for (CursoUsuario item : cursoUsuarios) {
            ids.add(item.getId());
        }

        // buscar los usuaros de la api usuarios
        List<Usuario> usuarios = usuarioClientRest.getUsuarios(ids);
        Curso curso = byOptional.get();

        // agregar los usuariso al objero curso
        curso.setUsuarios(usuarios);

        // respuesta
        return ResponseEntity.ok().body(curso);
    }

    /**
     * Crear un nuevo curso
     * 
     * @param curso
     * @param bindingResult
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> store(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
    }

    /**
     * Actualizar un curso
     * 
     * @param usuario
     * @param bindingResult
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Curso usuario, BindingResult bindingResult, @PathVariable Long id) {

        // validar que exista id
        Optional<Curso> byOptional = cursoService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        usuario.setId(id);
        usuario = cursoService.save(usuario);
        return ResponseEntity.ok().body(usuario);

    }

    /**
     * Eliminar un curso
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        // validar que exista el id
        Optional<Curso> byOptional = cursoService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursoService.findById(id));

    }

    @PostMapping("/curso/usuario")
    public ResponseEntity<?> addUsuario(@RequestBody CursoUsuario cursoUsuario) {
        Map<String, String> errors = new HashMap<>();

        // validar si existe el curso
        Optional<Curso> findById = cursoService.findById(cursoUsuario.getCursoId());
        if (!findById.isPresent()) {
            errors.put("cursoId", "Id del curso no exite");
            return ResponseEntity.badRequest().body(errors);
        }

        // validar si existe el usuario en la api
        Usuario usuario = usuarioClientRest.getUsuario(cursoUsuario.getUsuarioId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cursoUsuarioService.addUsuario(cursoUsuario));

    }

    @DeleteMapping("/curso/usuario")
    public ResponseEntity<?> removeUsuario(@RequestBody CursoUsuario cursoUsuario) {
        Map<String, String> errors = new HashMap<>();

        //validar si existe el curso
        Optional<Curso> findById = cursoService.findById(cursoUsuario.getCursoId());
        if (!findById.isPresent()) {
            errors.put("cursoId", "Id del curso no exite");
            return ResponseEntity.badRequest().body(errors);
        }

        // //validar si existe el usuario en la api
        Usuario usuario = usuarioClientRest.getUsuario(cursoUsuario.getUsuarioId());


        cursoUsuarioService.removeUsuario(cursoUsuario.getCursoId(), cursoUsuario.getUsuarioId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}