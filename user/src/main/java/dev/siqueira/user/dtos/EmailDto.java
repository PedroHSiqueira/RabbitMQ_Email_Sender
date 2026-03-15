package dev.siqueira.user.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EmailDto(@NotNull UUID userId, @NotBlank @Email String emailTo, String emailSubject, String body) {
}
