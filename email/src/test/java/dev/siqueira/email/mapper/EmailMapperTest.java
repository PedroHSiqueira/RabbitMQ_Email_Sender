package dev.siqueira.email.mapper;

import dev.siqueira.email.dtos.EmailDto;
import dev.siqueira.email.entity.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EmailMapperTest {

    @Test
    @DisplayName("Convert an EmailDto to an Email ")
    void toEmailSuccessfully() {
        EmailDto emailDto = new EmailDto(UUID.randomUUID(), "Teste Unitário", "amaral@gmail.com", "Email Junit");

        Email email = EmailMapper.toEmail(emailDto);

        assertNotNull(email);
        assertEquals(email.getUserId(), emailDto.userId());
        assertEquals(email.getEmailTo(), emailDto.emailTo());
        assertEquals(email.getBody(), emailDto.body());
    }


    @Test
    @DisplayName("Convert an Email to an EmailDto")
    void toEmailDto() {
        Email email = new Email();
        email.setUserId(UUID.randomUUID());
        email.setEmailSubject("Teste Unitário");
        email.setEmailFrom("siqueira@gmail.com");
        email.setEmailTo("amaral@gmail.com");
        email.setBody("Email Junit");

        EmailDto emailDto = EmailMapper.toEmailDto(email);

        assertNotNull(emailDto);
        assertEquals(emailDto.userId(), email.getUserId());
        assertEquals(emailDto.emailSubject(), email.getEmailSubject());
        assertEquals(emailDto.body(), email.getBody());
    }
}