package com.wikimedia.kafka.producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaRecentChangeProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentChangeProducer.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaRecentChangeProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRecentChange() throws InterruptedException {

        // to read real time event data from wikimedia, we use event source
        EventHandler handler = new WikimediaChangesHandler(kafkaTemplate, topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventSource.Builder builder = new EventSource.Builder(handler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();
        TimeUnit.MINUTES.sleep(10);
    }
}
