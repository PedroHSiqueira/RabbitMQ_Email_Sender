package dev.siqueira.user.controller;

import dev.siqueira.user.dtos.UserDto;
import dev.siqueira.user.entity.User;
import dev.siqueira.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Should save an User in Database")
    void saveSuccessufully() throws Exception {
        UserDto dto = new UserDto("Pedro Siqueira", "siqueira@gmail.com");
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setUserId(id);
        user.setName("Pedro");
        user.setEmail("pedro@email.com");

        Mockito.when(userService.save(Mockito.any(User.class)))
                .thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pedro"))
                .andExpect(jsonPath("$.email").value("pedro@email.com"));

        Mockito.verify(userService, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Should delete an User in Database")
    void delete() throws Exception {
        UUID id = UUID.randomUUID();

        User user = new User();
        user.setUserId(id);
        user.setName("Pedro");
        user.setEmail("pedro@email.com");

        Mockito.when(userService.findById(id)).thenReturn(user);
        Mockito.doNothing().when(userService).delete(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(userService, Mockito.times(1)).findById(id);
        Mockito.verify(userService, Mockito.times(1)).delete(user);
    }
}