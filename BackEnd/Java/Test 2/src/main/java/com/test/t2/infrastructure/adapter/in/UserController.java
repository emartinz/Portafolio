package com.test.t2.infrastructure.adapter.in;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.t2.application.dto.ApiResponseDTO;
import com.test.t2.application.service.UserService;
import com.test.t2.domain.model.entities.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Controlador para operaciones relacionadas con Usuarios.")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Metodo para listar todos los usuarios.
     * 
     * @return ResponseEntity con la lista de usuarios o un mensaje de error
     */
    @Operation(summary = "Listar todos los usuarios.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Consulta Exitosa", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "204", description = "No se encontraron usuarios.", 
            content = @Content),
        @ApiResponse(responseCode = "400", description = "Ocurrio un error.", 
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponseDTO<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
    
        // Verifica si la lista está vacía y responde con HTTP 204 No Content
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(new ApiResponseDTO<>("success", "", users));
    }

    /**
     * Metodo para obtener un usuario por su id.
     * 
     * @param id el id del usuario a obtener
     * @return ResponseEntity con el usuario o un mensaje de error
     */
    @Operation(summary = "Obtener un usuario por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario obtenido correctamente",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponseDTO<User>> getUserById(@PathVariable String id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "User retrieved successfully", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    /**
     * Metodo para crear un nuevo usuario.
     * 
     * @param user los datos del usuario a crear
     * @return ResponseEntity con el usuario creado o un mensaje de error
     */
    @Operation(summary = "Crear un nuevo usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "400", description = "Error al crear el usuario",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<User>> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponseDTO<>("success", "User created successfully", createdUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    /**
     * Metodo para actualizar los datos de un usuario.
     * 
     * @param id el id del usuario a actualizar
     * @param user los nuevos datos del usuario
     * @return ResponseEntity con el usuario actualizado o un mensaje de error
     */
    @Operation(summary = "Actualizar los datos de un usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponseDTO<User>> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "User updated successfully", updatedUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    /**
     * Metodo para eliminar un usuario.
     * 
     * @param id el id del usuario a eliminar
     * @return ResponseEntity con un mensaje de éxito o error
     */
    @Operation(summary = "Eliminar un usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteUser(@PathVariable String id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "User deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }

    /**
     * Metodo para obtener el balance de un usuario.
     * 
     * @param userId el id del usuario para obtener el balance
     * @return ResponseEntity con el balance del usuario o un mensaje de error
     */
    @Operation(summary = "Obtener el balance de un usuario.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Balance obtenido correctamente",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) }),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = ApiResponseDTO.class)) })
    })
    @GetMapping("/getBalance/{userId}")
    public ResponseEntity<ApiResponseDTO<Double>> getBalance(@PathVariable String userId) {
        try {
            double balance = userService.getBalance(userId);
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "Balance retrieved successfully", balance));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponseDTO<>("error", e.getMessage(), null));
        }
    }
}