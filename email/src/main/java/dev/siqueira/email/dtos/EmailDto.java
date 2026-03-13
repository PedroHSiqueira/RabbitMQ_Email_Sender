package dev.siqueira.email.dtos;

import java.util.UUID;

public record EmailDto(UUID emailId, String subject, String body) {

}
