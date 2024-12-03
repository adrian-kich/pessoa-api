package com.adriankich.pessoaapi.domain.service;

import com.adriankich.pessoaapi.domain.dto.EmailVerificationDTO;
import com.adriankich.pessoaapi.domain.model.Pessoa;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final String TOPIC = "send-email-verification";

    private final KafkaTemplate<String, EmailVerificationDTO> kafkaTemplateMail;

    public EmailService(KafkaTemplate<String, EmailVerificationDTO> kafkaTemplateMail) {
        this.kafkaTemplateMail = kafkaTemplateMail;
    }

    @SuppressWarnings("null")
    public void sendMessageVerification(Pessoa pessoa) {
        EmailVerificationDTO emailVerificationDTO = new EmailVerificationDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getEmail(),
                pessoa.getCpf());

        kafkaTemplateMail.send(TOPIC, emailVerificationDTO);
    }
}
