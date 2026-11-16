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

import com.example.demo.model.Producto;
import com.example.demo.model.Image;
import com.example.demo.repo.imagenRepo;
import com.example.demo.repo.productoRepo;

// Imports de Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
public class ImagenController {

    @Autowired
    imagenRepo repo;
    @Autowired
    productoRepo repoProducto;

    @Operation(summary = "Listar todas las imágenes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de imágenes devuelta exitosamente")
    })
    @GetMapping("imagenes")
    public List<Image> findAllimagenes() {
        return this.repo.findAll();
    }

    @Operation(summary = "Agregar una imagen sin producto asociado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen guardada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping("imagen")
    public Image addimagen(@RequestBody Image imagen) {
        return repo.save(imagen);
    }

    @Operation(summary = "Agregar imagen a un producto específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen asociada y guardada"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PostMapping("imagen/{productId}")
    public Image addimagen(@RequestBody Image imagen,@PathVariable String productId) {
        Optional<Producto> p = repoProducto.findById(productId);
        if(p.isPresent()){
            imagen.setProducto(p.get());
        }
        return repo.save(imagen);
    }

    @Operation(summary = "Obtener imágenes por ID de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imágenes encontradas"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("imagen/{productId}")
    public Set<Image>  getImagensByProduct(@PathVariable String productId) {
        Optional<Producto> p = repoProducto.findById(productId);
        if(p.isPresent()){
            return  p.get().getImagenes();
        }
        return null;
    }

    @Operation(summary = "Obtener una imagen por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen encontrada"),
        @ApiResponse(responseCode = "404", description = "Imagen no encontrada", content = @Content)
    })
    @GetMapping("imagen/{id}")
    public Optional<Image> getById(@PathVariable String id) {
        return repo.findById(id);
    }

    @Operation(summary = "Actualizar una imagen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Imagen no encontrada", content = @Content)
    })
    @PutMapping("imagen/{id}")
    public Image putusuari(@PathVariable String id, @RequestBody Image imagen) {
        imagen.setId(id);
        return repo.save(imagen);
    }

    @Operation(summary = "Eliminar una imagen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Imagen eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Imagen no encontrada", content = @Content)
    })
    @DeleteMapping("imagen/{id}")
    public String deleteMailbox(@PathVariable String id) {
        repo.deleteById(id);
        return "{\"success\":true}";
    }
}