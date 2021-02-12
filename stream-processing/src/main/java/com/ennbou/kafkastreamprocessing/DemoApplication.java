package com.ennbou.kafkastreamprocessing;

import com.ennbou.kafkastreamprocessing.entities.Operation;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

@Bean
public Function<KStream<String, Operation>, KStream<String, Long>> compteOperations() {
  return (input) -> {
    return input.
            map((k, v) -> {
              System.out.println("received " + v);
              return new KeyValue<>(v.getClienId(), v.getClienId());
            }).
            groupByKey(Grouped.with(Serdes.Long(), Serdes.Long())).
            windowedBy(TimeWindows.of(Duration.ofSeconds(5))).
            count(Materialized.as("compte-operations")).
            toStream().
            map((k, v) -> new KeyValue<>(k.window().startTime() + " | Id of client : " + k.key(), v));
  };
}
}
