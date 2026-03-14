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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private ResponseEntity<User> save(@RequestBody UserDto userDto) {
        User userSaved = userService.save(UserMapper.toEntity(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }
}
