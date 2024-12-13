package com.test.t2.infrastructure.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.t2.application.service.TransactionService;
import com.test.t2.domain.model.entities.Transaction;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transactions", description = "Controlador para operaciones relacionadas con Transacciones.")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Método que obtiene el historial de transacciones de un usuario.
     * @param userId Id del usuario
     * @return Lista de transacciones
     */
    @Operation(summary = "Obtener el historial de transacciones de un usuario.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Consulta Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content),
    })
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String userId) {
        List<Transaction> transactions = transactionService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactions);
    }

    /**
     * Método que obtiene transacciones de un usuario según su tipo.
     * @param userId Id del usuario
     * @param type Tipo de transacción (subscription o cancellation)
     * @return Lista de transacciones filtradas por tipo
     */
    @Operation(summary = "Obtener transacciones de un usuario según su tipo.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Consulta Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content),
    })
    @GetMapping("/get/{userId}/{type}")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@PathVariable String userId, @PathVariable String type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(userId, type);
        return ResponseEntity.ok(transactions);
    }
}
