package com.ms.user.client.viacep.dto;

public record ViaCepAddress(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
) {
}
