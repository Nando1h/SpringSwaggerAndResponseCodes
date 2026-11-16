package com.example.demo.controllers;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Comment;
import com.example.demo.model.Producto;
import com.example.demo.repo.comentarioRepo;
import com.example.demo.repo.productoRepo;

// Imports de Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
public class ComentarioController {

    @Autowired
    comentarioRepo repo;
    @Autowired
    productoRepo repoProducto;

    @Operation(summary = "Listar todos los comentarios")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de comentarios devuelta exitosamente")
    })
    @GetMapping("comentarios")
    public List<Comment> findAllcomentarios() {
        return this.repo.findAll();
    }

    @Operation(summary = "Agregar un comentario sin producto asociado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario guardado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("comentario")
    public Comment addcomentario(@RequestBody Comment comentario) {
        return repo.save(comentario);
    }

    @Operation(summary = "Agregar comentario a un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario asociado y guardado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PostMapping("comentario/{productId}")
    public Comment addcomentario(@RequestBody Comment comentario,@PathVariable String productId) {
        Optional<Producto> p = repoProducto.findById(productId);
        if(p.isPresent()){
            System.out.println(p.get().getNombre());
            comentario.setProducto(p.get());
        }
        return repo.save(comentario);
    }

    @Operation(summary = "Obtener comentarios por ID de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentarios encontrados"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("comentarios/{productId}")
    public Set<Comment>  getCommentsByProduct(@PathVariable String productId) {
        Optional<Producto> p = repoProducto.findById(productId);
        if(p.isPresent()){
            return  p.get().getComentarios();
        }
        return null;
    }

    @Operation(summary = "Obtener un comentario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario encontrado"),
        @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    @GetMapping("comentario/{id}")
    public Optional<Comment> getById(@PathVariable String id) {
        return repo.findById(id);
    }

    @Operation(summary = "Actualizar un comentario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    @PutMapping("comentario/{id}")
    public Comment putusuari(@PathVariable String id, @RequestBody Comment comentario) {
        comentario.setId(id);
        return repo.save(comentario);
    }

    @Operation(summary = "Eliminar un comentario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comentario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Comentario no encontrado", content = @Content)
    })
    @DeleteMapping("comentario/{id}")
    public String deleteMailbox(@PathVariable String id) {
        repo.deleteById(id);
        return "{\"success\":true}";
    }
}