package edu.ensias.kafka;

import java.util.Properties;
import java.util.Arrays;
import java.time.Duration;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EventConsumer {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Entrer le nom du topic");
            return;
        }

        String topicName = args[0];

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        // Try-with-resources ensures consumer is closed automatically
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList(topicName));
            System.out.println("Souscris au topic " + topicName);

            // Add a shutdown hook to close the consumer gracefully on Ctrl+C
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nArrêt du consumer...");
                consumer.wakeup(); // Interrupt any ongoing poll
            }));

            while (true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("offset = %d, key = %s, value = %s%n",
                                record.offset(), record.key(), record.value());
                    }
                } catch (org.apache.kafka.common.errors.WakeupException e) {
                    // This exception is expected on shutdown
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Consumer fermé avec succès.");
    }
}
