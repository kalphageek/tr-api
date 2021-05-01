package me.kalpha.trapi.ehub.service;

import lombok.extern.slf4j.Slf4j;
import me.kalpha.trapi.ehub.entity.Eqp1Tr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class Eqp1TrProducerService {

    @Value("${app.topic.name}")
    private String TOPIC;

    private final KafkaTemplate<String, Eqp1Tr> eqp1TrKafkaTemplate;

    @Autowired
    public Eqp1TrProducerService(KafkaTemplate<String, Eqp1Tr> eqp1TrKafkaTemplate) {
        this.eqp1TrKafkaTemplate = eqp1TrKafkaTemplate;
    }

    public void sendMessage(Eqp1Tr eqp1Tr) {
        sendMessage(TOPIC, eqp1Tr);
    }

    public void sendMessage(String topic, Eqp1Tr eqp1Tr) {
        Message<Eqp1Tr> message = MessageBuilder.withPayload(eqp1Tr)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        ListenableFuture<SendResult<String, Eqp1Tr>> future = eqp1TrKafkaTemplate.send(message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Eqp1Tr>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[{}({})] due to : [{}]", eqp1Tr, eqp1Tr.getEqp1TrDets().size(), ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Eqp1Tr> result) {
                log.info("Sent message=[{}({})] with offset=[{}]", eqp1Tr, eqp1Tr.getEqp1TrDets().size(), result.getRecordMetadata().offset());
            }
        });
    }
}

