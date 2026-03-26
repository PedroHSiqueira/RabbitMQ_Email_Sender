package dev.siqueira.user.producer;

import dev.siqueira.user.dtos.EmailDto;
import dev.siqueira.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserProducerTest {

    @InjectMocks
    private UserProducer userProducer;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private User user;

    @Mock
    private EmailDto emailCreateDto;

    @Mock
    private EmailDto emailDeleteDto;

    @BeforeEach
    void setup(){
        user = new User();
        user.setUserId(UUID.randomUUID());
        user.setName("Pedro Siqueira");
        user.setEmail("siqueira@gmail.com");

        emailCreateDto = new EmailDto(
                user.getUserId(),
                user.getEmail(),
                "Bem vindo ao serviço de mensageria usando RabbitMQ!",
                "Olá " + user.getName() + ", Ficamos muito felizes de ter você conosco para este serviço de mensageria, o mesmo foi desenvolvido para estudos sobre RabbitMQ, Ass: Pedro Siqueira"

        );

        emailDeleteDto = new EmailDto(
                user.getUserId(),
                user.getEmail(),
                "Remoção de Usuário",
                "O usuário: " + user.getName() + " Foi removido do sistema!");

    }

    @Test
    @DisplayName("Should send a create event to rabbit template")
    void sendEventAfterCreateSuccessfully() {
        Mockito.doNothing().when(rabbitTemplate).convertAndSend("", "email-queue", emailCreateDto);

        userProducer.sendEventAfterCreate(user);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend("",
                "email-queue",
                emailCreateDto);
    }

    @Test
    @DisplayName("Should send a delete event to rabbit template")
    void sendEventAfterDelete() {
        Mockito.doNothing().when(rabbitTemplate).convertAndSend("", "email-queue", emailDeleteDto);

        userProducer.sendEventAfterDelete(user);

        Mockito.verify(rabbitTemplate, Mockito.times(1)).convertAndSend("",
                "email-queue",
                emailDeleteDto);
    }
}