package com.test.t1.adapter.in.controller;

import org.springframework.web.bind.annotation.RestController;

import com.test.t1.application.service.CompanyService;
import com.test.t1.domain.model.dto.CompanyDTO;
import com.test.t1.domain.model.entity.Company;

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
@RequestMapping("/api/company")
@Tag(name = "Company", description = "Controlador para compañias.")
public class CompanyController {

    @Autowired
    private CompanyService service;

    
    @Operation(summary = "Listar todas las compañias.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Muestra la lista de compañias", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content), 
        @ApiResponse(responseCode = "404", description = "No se encontraron compañias.", 
            content = @Content) 
    })
    @GetMapping("/getAll")
    public List<Company> getAll () {
        return service.getAll();
    }

    @Operation(summary = "Mostrar una compañia basandose en su id.")
    @GetMapping("/getById/{id}")
    public ResponseEntity<Company> getById(@Parameter(description = "id de la compañia a consultar.") @PathVariable long id) {
        Optional<Company> response = service.getById(id);
        return response.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Agregar una nueva compañia.")
    @PostMapping("/add")
    public ResponseEntity<Company> add(@RequestBody Company object) {
        Company newObject = service.add(object);
        return ResponseEntity.status(HttpStatus.CREATED).body(newObject);
    }

    @Operation(summary = "Actualizar una compañia existente.")
    @PutMapping("/update/{id}")
    public ResponseEntity<Company> update (@Parameter(description = "id de la compañia a modificar.") @PathVariable long id,
        @RequestBody Company updatedObject) {
        Company object = service.update(id, updatedObject);
        return ResponseEntity.ok(object);
    }

    @Operation(summary = "Borrar una compañia")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "id de la compañia a borrar.") @PathVariable long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByCode/{code}")
    @Operation(summary = "Mostrar una compañia basandose en su codigo.")
    public ResponseEntity<CompanyDTO> getCompanyDetailsByCodigo(@Parameter(description = "codigo de la compañia a consultar.") @PathVariable String code) {
        Optional<CompanyDTO> companyDTO = service.getCompanyDetailsByCodigo(code);

        if (companyDTO.isPresent()) {
            return ResponseEntity.ok(companyDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
