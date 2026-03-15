package dev.siqueira.email.entity;

import dev.siqueira.email.enums.EmailStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_EMAIL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Email {

    private final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;
    private UUID userId;
    private String emailTo;
    private String emailFrom;
    private String emailSubject;
    private String body;
    private LocalDateTime sentDate;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
}
