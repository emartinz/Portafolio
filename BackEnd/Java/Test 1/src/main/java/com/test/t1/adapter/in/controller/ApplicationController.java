package com.test.t1.adapter.in.controller;

import org.springframework.web.bind.annotation.RestController;

import com.test.t1.application.service.ApplicationService;
import com.test.t1.domain.model.entity.Application;

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
@RequestMapping("/api/application")
@Tag(name = "Application", description = "Controlador para aplicaciones.")
public class ApplicationController {

    @Autowired
    private ApplicationService service;

    
    @Operation(summary = "Listar todas las aplicaciones.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Muestra la lista de aplicaciones", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "No se encontraron aplicaciones.", 
            content = @Content) 
    })
    @GetMapping("/getAll")
    public List<Application> getAll () {
        return service.getAll();
    }

    @Operation(summary = "Mostrar una aplicacion basandose en su id.")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Application> getById(@Parameter(description = "id de la aplicacion a consultar.") @PathVariable long id) {
        Optional<Application> response = service.getById(id);
        return response.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Agregar una nueva aplicacion.")
    @PostMapping("/add")
    public ResponseEntity<Application> add(@RequestBody Application object) {
        Application newObject = service.add(object);
        return ResponseEntity.status(HttpStatus.CREATED).body(newObject);
    }

    @Operation(summary = "Actualizar una aplicacion existente.")
    @PutMapping("/update/{id}")
    public ResponseEntity<Application> update (@Parameter(description = "id de la aplicacion a modificar.") @PathVariable long id,
        @RequestBody Application updatedObject) {
        Application object = service.update(id, updatedObject);
        return ResponseEntity.ok(object);
    }

    @Operation(summary = "Borrar una aplicacion")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "id de la aplicacion a borrar.") @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
