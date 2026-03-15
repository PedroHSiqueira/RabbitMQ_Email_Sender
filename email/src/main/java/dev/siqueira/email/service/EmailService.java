package dev.siqueira.email.service;

import dev.siqueira.email.entity.Email;
import dev.siqueira.email.enums.EmailStatus;
import dev.siqueira.email.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    public EmailService(JavaMailSender mailSender, EmailRepository emailRepository) {
        this.mailSender = mailSender;
        this.emailRepository = emailRepository;
    }

    @Value("${EMAIL_USERNAME}")
    private String emailFrom;

    @Transactional
    public void sendEmail(Email email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(email.getEmailTo());
            message.setSubject(email.getEmailSubject());
            message.setText(email.getBody());
            mailSender.send(message);
            email.setStatus(dev.siqueira.email.enums.EmailStatus.SENT);
            email.setSentDate(LocalDateTime.now());
        } catch (Exception e) {
            email.setStatus(EmailStatus.FAILED);
            System.out.println("Erro ao enviar email: " + e.getMessage());
        }

        emailRepository.save(email);
    }

}