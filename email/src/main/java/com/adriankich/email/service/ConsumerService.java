package com.adriankich.email.service;

import com.adriankich.email.dto.PessoaMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @Autowired
    private EmailService emailService;

    private final String url = "http://localhost:8080/pessoas";

    @KafkaListener(topicPartitions = @TopicPartition(topic = "send-email-verification", partitions = {"0"}),
            containerFactory = "emailKafkaListenerContainerFactory")
    public void listener(PessoaMessageDTO pessoaMessageDTO) {
        String message = "clique no link para fazer a verificação: " + url + "/" + pessoaMessageDTO.id() + "/verify";

        emailService.sendMail(pessoaMessageDTO.email(),
                pessoaMessageDTO.name() + " - " + pessoaMessageDTO.cpf(),
                message);
    }
}
