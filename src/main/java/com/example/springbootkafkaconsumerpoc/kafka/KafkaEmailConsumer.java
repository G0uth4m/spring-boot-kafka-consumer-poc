package com.example.springbootkafkaconsumerpoc.kafka;

import com.example.common.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaEmailConsumer {

  @KafkaListener(
      topics = "uhctcxjl-test-topic",
      groupId = "uhctcxjl-consumers",
      containerFactory = "emailConcurrentKafkaListenerContainerFactory"
  )
  public void sendEmail(Email email) {
    log.info("Email: {}", email);
  }
}
