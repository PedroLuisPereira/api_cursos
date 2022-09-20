package com.example.usuarios.controller;

import com.example.usuarios.entity.Usuario;
import com.example.usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public List<Usuario> index() {
        return usuarioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        Optional<Usuario> byOptional = usuarioService.findById(id);
        if (byOptional.isPresent()) {
            return ResponseEntity.ok().body(byOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> store(@Valid @RequestBody Usuario usuario, BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }

        if(usuarioService.findByEmail(usuario.getEmail()).isPresent()){
            errors.put("email","El email ya está en uso") ;
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Usuario usuario, BindingResult bindingResult, @PathVariable Long id) {

        //validaciones
        Optional<Usuario> byOptional = usuarioService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
            return ResponseEntity.badRequest().body(errors);
        }


        //validar id
        if(usuarioService.findByEmail(usuario.getEmail()).isPresent()){
            errors.put("email","El email ya está en uso") ;
            return ResponseEntity.badRequest().body(errors);
        }

        usuario.setId(id);
        usuario = usuarioService.save(usuario);
        return ResponseEntity.ok().body(usuario);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        //validar que exista el id
        Optional<Usuario> byOptional = usuarioService.findById(id);
        if (!byOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuarioService.findById(id));

    }


}
