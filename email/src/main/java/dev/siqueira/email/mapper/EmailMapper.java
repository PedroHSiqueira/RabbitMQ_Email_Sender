package dev.siqueira.email.mapper;

import dev.siqueira.email.dtos.EmailDto;
import dev.siqueira.email.entity.Email;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class EmailMapper {

    @Value("${EMAIL_USERNAME}")
    private String emailFrom;

    public Email toEmail(EmailDto emailDto){
        Email email = new Email();
        email.setUserId(emailDto.userId());
        email.setEmailSubject(emailDto.emailSubject());
        email.setEmailFrom(emailFrom);
        email.setEmailTo(emailDto.emailTo());
        email.setBody(emailDto.body());

        return email;
    }

    public EmailDto toEmailDto(Email email){
        return new EmailDto(email.getUserId(), email.getEmailTo(), email.getEmailSubject(), email.getBody());
    }
}
