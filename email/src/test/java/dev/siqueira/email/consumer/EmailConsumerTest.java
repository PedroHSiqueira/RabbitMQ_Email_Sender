package dev.siqueira.email.consumer;

import dev.siqueira.email.dtos.EmailDto;
import dev.siqueira.email.entity.Email;
import dev.siqueira.email.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class EmailConsumerTest {

    @InjectMocks
    private EmailConsumer emailConsumer;

    @Mock
    private EmailService emailService;

    @Mock
    private EmailDto emailDto;

    @BeforeEach
    void setup(){
        emailDto = new EmailDto(UUID.randomUUID(), "Teste Unitário", "amaral@gmail.com", "Email Junit");
    }

    @Test
    @DisplayName("Create a listener to RabbitMQ")
    void listenEmailQueueListenSuccessfully() {
        Mockito.doNothing().when(emailService).sendEmail(Mockito.any(Email.class));

        emailConsumer.listenEmailQueue(emailDto);

        Mockito.verify(emailService, Mockito.times(1)).sendEmail(Mockito.any(Email.class));
    }
}