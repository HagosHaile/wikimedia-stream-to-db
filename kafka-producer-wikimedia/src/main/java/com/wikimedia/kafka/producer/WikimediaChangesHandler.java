package com.wikimedia.kafka.producer;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class WikimediaChangesHandler implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentChangeProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public WikimediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }
    @Override
    public void onOpen() {

    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) {
        LOGGER.info("Received Wikimedia changes: {}", messageEvent);
        kafkaTemplate.send(topic, messageEvent.getData());
        LOGGER.info("kafka Message sent: {}", messageEvent.getData());
    }

    @Override
    public void onComment(String s) {

    }

    @Override
    public void onError(Throwable throwable) {

    }
}
