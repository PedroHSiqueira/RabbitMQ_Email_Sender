package dev.siqueira.user.controller;

import dev.siqueira.user.dtos.UserDto;
import dev.siqueira.user.entity.User;
import dev.siqueira.user.mapper.UserMapper;
import dev.siqueira.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuários", description = "Endpoint responsável pelo gerenciamento do usuário!")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Cria Usuário", description = "Rota responsavel pela criação do Usuário no serviço")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!")
    public ResponseEntity<User> save(@RequestBody UserDto userDto) {
        User userSaved = userService.save(UserMapper.toEntity(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta Usuário", description = "Endpoint de deleção do sistema!")
    @ApiResponse(responseCode = "204", description = "Usuário Deletado")
    public ResponseEntity<User> delete(@PathVariable UUID id) {
        User user = userService.findById(id);
        userService.delete(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
