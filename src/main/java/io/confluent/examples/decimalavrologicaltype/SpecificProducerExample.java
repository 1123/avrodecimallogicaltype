package io.confluent.examples.decimalavrologicaltype;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.util.Properties;

public class SpecificProducerExample {

    public static void main(final String[] args) {
        final Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        Conversions.DecimalConversion decimalConversion = new Conversions.DecimalConversion();
        try (KafkaProducer<String, Payment> producer = new KafkaProducer<>(props)) {
            for (long i = 0; i < 10; i++) {
                producer.send(
                    new ProducerRecord<>(
                        "transactions", "id-" + i,
                        new Payment(
                                "id-" + i,
                                1000.00d,
                                // Make sure that the precision and scale specified below are the same as in the Avro Schema Definition file.
                                // Otherwise the data will not be interpreted correctly by the JDBC Sink connector -- and by other applications as well.
                                decimalConversion.toBytes(new BigDecimal("1.45"), null, LogicalTypes.decimal(30,9))
                        )
                    )
                );
            }
            producer.flush();
        } catch (final SerializationException e) {
            e.printStackTrace();
        }

    }

}
