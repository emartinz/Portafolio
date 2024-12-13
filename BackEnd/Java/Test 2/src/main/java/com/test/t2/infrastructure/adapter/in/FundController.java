package com.test.t2.infrastructure.adapter.in;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.t2.application.dto.ApiResponseDTO;
import com.test.t2.application.dto.SubscriptionRequestDTO;
import com.test.t2.application.service.FundService;
import com.test.t2.domain.model.entities.Fund;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/funds")
@Tag(name = "Funds", description = "Controlador para operaciones relacionadas con Fondos de Inversion.")
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    /**
     * Suscribir a un usuario a un fondo.
     * @param userId ID del usuario que se va a suscribir
     * @param fundId ID del fondo al que se va a suscribir
     * @param investmentAmount Monto a invertir en el fondo
     * @return Respuesta con éxito o error
     */
    @Operation(summary = "Suscribir a un usuario a un fondo.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Suscripcion Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
    })
    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponseDTO<Object>> subscribeToFund(@RequestBody SubscriptionRequestDTO subscriptionRequest) {
        try {
            // Validar que investmentAmount no sea nulo
            if (Objects.isNull(subscriptionRequest.getInvestmentAmount())) {
                return ResponseEntity.badRequest().body(new ApiResponseDTO<>(
                    "error", 
                    "El monto de inversión no puede ser nulo o cero.", 
                    null
                ));
            }

            // Validar que investmentAmount sea positivo (si no lo es, devolver error)
            if (subscriptionRequest.getInvestmentAmount() <= 0) {
                return ResponseEntity.badRequest().body(new ApiResponseDTO<>(
                    "error", 
                    "El monto de inversión debe ser mayor a cero.",
                    null
                ));
            }
            fundService.subscribeToFund(subscriptionRequest.getUserId(), subscriptionRequest.getFundId(), subscriptionRequest.getInvestmentAmount());
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "Subscripción exitosa.", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    /**
     * Cancelar la suscripción de un usuario a un fondo.
     * @param userId ID del usuario que cancela la suscripción
     * @param fundId ID del fondo del que se cancela la suscripción
     * @return Respuesta con éxito o error
     */
    @Operation(summary = "Cancelar la suscripción de un usuario a un fondo.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Cancelacion Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @PostMapping("/cancel")
    public ResponseEntity<ApiResponseDTO<Object>> cancelSubscription(@RequestBody SubscriptionRequestDTO subscriptionRequest) {
        try {
            fundService.cancelSubscription(subscriptionRequest.getUserId(), subscriptionRequest.getFundId());
            return ResponseEntity.ok(new ApiResponseDTO<>(
                "success", 
                "Cancelación exitosa.",
                null
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>(
                "error", e.getMessage(), null
            ));
        }
    }
    
    /**
     * Método para obtener lista de Fondos disponibles
     * @param entity
     * @return
     */
    @Operation(summary = "Obtener lista de Fondos de Inversion definidos en BD.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Consulta Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = List.class)) }),
        @ApiResponse(responseCode = "204", description = "No se encontraron fondos de Inversion definidos en BD.", 
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = @Content)
    })
    @GetMapping("/getList")
    public ResponseEntity<ApiResponseDTO<List<Fund>>> getList() {
        List<Fund> funds = fundService.getFundsList();
    
        // Verifica si la lista está vacía y responde con HTTP 204 No Content
        if (funds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "Fondos encontrados.", funds));
    }
}