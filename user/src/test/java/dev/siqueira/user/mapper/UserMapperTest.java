package dev.siqueira.user.mapper;

import dev.siqueira.user.dtos.UserDto;
import dev.siqueira.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Test
    @DisplayName("Should convert an UserDTO to an User")
    void toEntitySuccessfully() {
        UserDto dto = new UserDto("Pedro Siqueira", "siqueira@gmail.com");

        User user = UserMapper.toEntity(dto);

        assertNotNull(user);
        assertEquals("Pedro Siqueira", user.getName());
        assertEquals("siqueira@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("Should convert an User to an UserDTO")
    void toDtoSuccessfully() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "Pedro Siqueira", "siqueira@gmail.com");

        UserDto dto = UserMapper.toDto(user);

        assertNotNull(dto);
        assertEquals("Pedro Siqueira", dto.name());
        assertEquals("siqueira@gmail.com", dto.email());
    }
}