package com.wikimedia.kafka.consumer;

import com.wikimedia.kafka.model.WikimediaData;
import com.wikimedia.kafka.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDbConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDbConsumer.class);

    private final WikimediaDataRepository wikimediaDataRepository;
    public KafkaDbConsumer(WikimediaDataRepository wikimediaDataRepository) {
        this.wikimediaDataRepository = wikimediaDataRepository;
    }
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage) {
        LOGGER.info("Received event message: {}", eventMessage);
        WikimediaData wikimediaData = new WikimediaData();
        String temp = eventMessage.length() > 200 ? eventMessage.substring(0, 200) : eventMessage;
        wikimediaData.setWikiEventData(temp);
        wikimediaDataRepository.save(wikimediaData);
    }
}
