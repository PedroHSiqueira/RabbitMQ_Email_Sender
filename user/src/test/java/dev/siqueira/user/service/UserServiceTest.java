package dev.siqueira.user.service;

import dev.siqueira.user.entity.User;
import dev.siqueira.user.producer.UserProducer;
import dev.siqueira.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserProducer userProducer;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("Pedro Siqueira");
        user.setEmail("siqueira@gmail.com");
    }

    @Test
    @DisplayName("Should create an user in Database and send a event to RabbitMQ")
    void saveUserSuccessfully() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.doNothing().when(userProducer).sendEventAfterCreate(user);

        User userSaved = userService.save(user);

        assertNotNull(userSaved);
        assertEquals(userSaved, user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Mockito.verify(userProducer, Mockito.times(1)).sendEventAfterCreate(user);
    }

    @Test
    @DisplayName("Should delete the user and send a event to RabbitMQ")
    void deleteUserSuccessfully() {
        Mockito.doNothing().when(userProducer).sendEventAfterDelete(user);

        userService.delete(user);

        Mockito.verify(userProducer, Mockito.times(1)).sendEventAfterDelete(user);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    @DisplayName("Should FindById return an User")
    void findByIdWithReturn() {
        Mockito.when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        User userDB = userService.findById(user.getUserId());

        assertEquals(user, userDB);

        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getUserId());
    }

    @Test
    @DisplayName("Should FindById return a null value")
    void findByIdWithNull() {
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        UUID id = UUID.randomUUID();

        User userDB = userService.findById(id);

        assertNull(userDB);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);
    }
}