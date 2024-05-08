package com.ms.user.client.viacep;

import com.ms.user.client.viacep.dto.ViaCepAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://viacep.com.br/ws/",name = "viacep")
public interface ViacepClient {

    @GetMapping("{cep}/json/")
    ViaCepAddress getAddress(@PathVariable String cep);
}
