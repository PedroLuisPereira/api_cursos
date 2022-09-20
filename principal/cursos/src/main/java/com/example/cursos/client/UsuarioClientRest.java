package com.example.cursos.client;

import com.example.cursos.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/")
    public Usuario index();

    @GetMapping("/{id}")
    public Usuario show(@PathVariable Long id);

    @PostMapping("/")
    public  Usuario store(@RequestBody Usuario usuario);

}
