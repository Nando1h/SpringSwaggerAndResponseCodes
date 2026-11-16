package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Producto;
import com.example.demo.repo.productoRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Imports de Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
public class ProductoController {
    @Autowired
    productoRepo repo;

    @Operation(summary = "Listar todos los productos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos devuelta exitosamente"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @GetMapping("productos")
    public List<Producto> findAllProductos() {
        return this.repo.findAll();
    }

    @Operation(summary = "Agregar un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos del producto inválidos", content = @Content)
    })
    @PostMapping("producto")
    public Producto addProducto(@RequestBody Producto producto) {
        return repo.save(producto);
    }

    @Operation(summary = "Obtener un producto por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("producto/{id}")
    public Optional<Producto> getById(@PathVariable String id) {
        return repo.findById(id);
    }

    @Operation(summary = "Actualizar un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("producto/{id}")
    public Producto putProduct(@PathVariable String id, @RequestBody Producto product) {
        product.setId(id);
        return repo.save(product);
    }

    @Operation(summary = "Eliminar un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @DeleteMapping("producto/{id}")
    public String deleteMailbox(@PathVariable("id") String id) {
        repo.deleteById(id);
        return "{\"success\":true}";
    }
}