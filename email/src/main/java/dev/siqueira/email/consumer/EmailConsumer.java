package dev.siqueira.email.consumer;

import dev.siqueira.email.dtos.EmailDto;
import dev.siqueira.email.entity.Email;
import dev.siqueira.email.mapper.EmailMapper;
import dev.siqueira.email.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void listenEmailQueue(@Payload EmailDto emailDto){
        Email email = EmailMapper.toEmail(emailDto);
        emailService.sendEmail(email);
    }
}
