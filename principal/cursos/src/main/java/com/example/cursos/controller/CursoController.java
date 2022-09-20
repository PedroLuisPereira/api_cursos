package com.example.cursos.controller;

import com.example.cursos.entity.Curso;
import com.example.cursos.entity.CursoUsuario;
import com.example.cursos.model.Usuario;
import com.example.cursos.service.CursoService;
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

   
    @GetMapping("/")
    public List<Curso> index() {
        return cursoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        //verficar si el curso existe
        Optional<Curso> byOptional = cursoService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        //buscar los usuarios del curso
        List<CursoUsuario> cursoUsuarios = cursoService.getCursoUsuarios(id);

        ArrayList<Long> ids= new ArrayList<Long>();

        for (CursoUsuario item : cursoUsuarios) {
            ids.add(item.getId());
        }

        List<Usuario> usuarios = cursoService.getUsuarios(ids);

        Curso curso = byOptional.get();
        curso.setUsuarios(usuarios);

        return ResponseEntity.ok().body(curso);
    }

   
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Curso usuario, BindingResult bindingResult, @PathVariable Long id ) {

        //validar que exista id
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
        usuario =  cursoService.save(usuario);
        return ResponseEntity.ok().body(usuario);

    }

    //@PutMapping("/curso/usuario")
    //public ResponseEntity<?> update(@RequestBody Curso usuario, BindingResult bindingResult, @PathVariable Long id ) { }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        //validar que exista el id
        Optional<Curso> byOptional = cursoService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(cursoService.findById(id));

    }


}
