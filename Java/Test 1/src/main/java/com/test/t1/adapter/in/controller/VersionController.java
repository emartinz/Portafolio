package com.test.t1.adapter.in.controller;

import org.springframework.web.bind.annotation.RestController;

import com.test.t1.application.service.VersionService;
import com.test.t1.domain.model.entity.Version;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse; 
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/version")
@Tag(name = "Version", description = "Controlador para versiones.")
public class VersionController {

    @Autowired
    private VersionService service;

    @Operation(summary = "Listar todas las versiones.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Muestra la lista de versiones", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "No se encontraron versiones.", 
            content = @Content) 
    })
    @GetMapping("/getAll")
    public List<Version> getAll () {
        return service.getAll();
    }

    @Operation(summary = "Mostrar una version basandose en su id.")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Version> getById(@Parameter(description = "id de la version a consultar.") @PathVariable long id) {
        Optional<Version> response = service.getById(id);
        return response.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Agregar una nueva version.")
    @PostMapping("/add")
    public ResponseEntity<Version> add(@RequestBody Version object) {
        Version newObject = service.add(object);
        return ResponseEntity.status(HttpStatus.CREATED).body(newObject);
    }

    @Operation(summary = "Actualizar una version existente.")
    @PutMapping("/update/{id}")
    public ResponseEntity<Version> update (@Parameter(description = "id de la version a modificar.") @PathVariable long id,
        @RequestBody Version updatedObject) {
        Version object = service.update(id, updatedObject);
        return ResponseEntity.ok(object);
    }

    @Operation(summary = "Borrar una version")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "id de la version a borrar.") @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
