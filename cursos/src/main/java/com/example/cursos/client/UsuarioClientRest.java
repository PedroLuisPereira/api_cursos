package com.example.cursos.client;

import com.example.cursos.model.Usuario;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/")
    public List<Usuario> getUsuarioAll();

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable Long id);

    @GetMapping("/curso/usuarios")
    public List<Usuario> getUsuarios(@RequestParam Iterable<Long> ids);

    @PostMapping("/")
    public  Usuario store(@RequestBody Usuario usuario);

}
