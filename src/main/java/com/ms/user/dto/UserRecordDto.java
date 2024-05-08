package com.ms.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRecordDto(@NotBlank String name,
                            @NotBlank @Email String email,
                            @NotBlank String password,
                            @NotBlank  String cep,
                            @NotBlank  String houseNumber,
                            @NotBlank  String complement) {
}
