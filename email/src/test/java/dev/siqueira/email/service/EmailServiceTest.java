package dev.siqueira.email.service;

import dev.siqueira.email.entity.Email;
import dev.siqueira.email.enums.EmailStatus;
import dev.siqueira.email.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private Email email;

    @BeforeEach
    void setup(){
        email = new Email();
        email.setEmailId(UUID.randomUUID());
        email.setUserId(UUID.randomUUID());
        email.setEmailTo("amaral@gmail.com");
        email.setEmailFrom("siqueira@gmail.com");
        email.setBody("Email Junit");
        email.setSentDate(LocalDateTime.now());
        email.setStatus(EmailStatus.PENDING);
    }

    @Test
    @DisplayName("Send an Email with success")
    void sendEmailSuccessfully() {
        Mockito.doNothing().when(mailSender).send(Mockito.any(SimpleMailMessage.class));

        emailService.sendEmail(email);

        Mockito.verify(mailSender, Mockito.times(1)).send(Mockito.any(SimpleMailMessage.class));
        Mockito.verify(emailRepository, Mockito.times(1)).save(email);
    }

    @Test
    @DisplayName("Send an Email with fail (Exception)")
    void sendEmailFail() {
        Mockito.doThrow(new RuntimeException("SMTP erro"))
                .when(mailSender)
                .send(Mockito.any(SimpleMailMessage.class));


        emailService.sendEmail(email);

        Mockito.verify(emailRepository, Mockito.times(1)).save(email);

        assertEquals(EmailStatus.FAILED, email.getStatus());
    }
}