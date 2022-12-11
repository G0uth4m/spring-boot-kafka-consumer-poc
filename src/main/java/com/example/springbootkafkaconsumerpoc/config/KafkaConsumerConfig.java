package com.example.springbootkafkaconsumerpoc.config;

import com.example.common.Email;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;

  @Value("${spring.kafka.consumer.group-id}")
  private String groupId;

  @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
  private String trustedPackages;

  @Value("${spring.kafka.listener.concurrency}")
  private Integer consumerFactoryConcurrency;

  @Value("${spring.kafka.properties.security.protocol}")
  private String securityProtocol;

  @Value("${spring.kafka.properties.sasl.mechanism}")
  private String saslMechanism;

  @Value("${spring.kafka.properties.sasl.jaas.config}")
  private String saslJaasConfig;

  @Bean
  public ConsumerFactory<String, Email> emailConsumerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    configProps.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackages);
    configProps.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
    configProps.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
    configProps.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);
    return new DefaultKafkaConsumerFactory<>(configProps);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Email> emailConcurrentKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Email> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(emailConsumerFactory());
    factory.setConcurrency(consumerFactoryConcurrency);
    return factory;
  }
}
