package com.example.cursos.entity;

import com.example.cursos.model.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    private String nombre;

    @Transient
    private List<Usuario> usuarios;


    public Curso() {
        usuarios = new ArrayList<>();
    }


    public Curso(Long id, @NotBlank String nombre, List<Usuario> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.usuarios = usuarios;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public List<Usuario> getUsuarios() {
        return usuarios;
    }


    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }


    @Override
    public String toString() {
        return "Curso [id=" + id + ", nombre=" + nombre + ", usuarios=" + usuarios + "]";
    }

}
