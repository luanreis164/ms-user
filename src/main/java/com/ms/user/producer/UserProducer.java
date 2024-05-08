package com.ms.user.producer;

import com.ms.user.model.User;
import com.ms.user.producer.dto.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;


    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String  routingKey;

    public void publishMessageEmail(User user){
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Cadastro realizado com sucesso!");
        emailDto.setText("<!DOCTYPE html><html lang='en'> <head><meta charset='UTF-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1.0'><title>Confirmação de Cadastro</title></head><body><table align='center' border='0' cellpadding='0' cellspacing='0' width='600'><tr><td align='center' bgcolor='#ffffff' style='padding: 40px 0 30px 0;'><h1>Bem-vindo," + user.getName() + "!</h1><p>Aproveite os descontos e preços do site e acompanhe as melhores ofertas.</p><p>Obrigado por se cadastrar!</p></td></tr></table></body></html>");

        rabbitTemplate.convertAndSend("",routingKey,emailDto);
    }

}
