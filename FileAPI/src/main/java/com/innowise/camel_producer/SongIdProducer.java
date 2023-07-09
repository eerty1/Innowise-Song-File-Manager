package com.innowise.camel_producer;

import com.innowise.model.SongFile;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongIdProducer {
    @Produce("aws2-sqs://songFileToEnricherService?amazonSQSClient=#sqsClient&autoCreateQueue=true")
    private final ProducerTemplate producerTemplate;

    @Autowired
    public SongIdProducer(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public void createMessageBody(SongFile songFile) {
        producerTemplate.sendBody(songFile.getId());
    }
}
