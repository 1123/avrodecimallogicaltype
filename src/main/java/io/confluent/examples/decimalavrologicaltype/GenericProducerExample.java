package io.confluent.examples.decimalavrologicaltype;

import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.IndexedRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Properties;

public class GenericProducerExample {

    private static final String TOPIC = "balances";
    private static final Properties props = new Properties();

    private static Schema createBalanceSchema() {
        String balanceSchema = "{\n" +
                "\t\"namespace\": \"example.avro\", \"type\": \"record\",\n" +
                "    \"name\": \"Account\",\n" +
                "    \"fields\": [\n" +
                "    \t{\"name\": \"accountNumber\", \"type\": \"string\"},\n" +
                "        {\"name\": \"balance\", \"type\": {\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":4,\"scale\":2}},\n" +
                "        {\"name\": \"date\", \"type\": {\"type\":\"int\",\"logicalType\":\"date\"}},\n" +
                "        {\"name\": \"timeMs\", \"type\": {\"type\":\"int\",\"logicalType\":\"time-millis\"}},\n" +
                "        {\"name\": \"timeMicros\", \"type\": {\"type\":\"long\",\"logicalType\":\"time-micros\"}},\n" +
                "        {\"name\": \"tsMs\", \"type\": {\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},\n" +
                "        {\"name\": \"tsMicros\", \"type\": {\"type\":\"long\",\"logicalType\":\"timestamp-micros\"}},\n" +
                "        {\"name\": \"localTsMs\", \"type\": {\"type\":\"long\",\"logicalType\":\"local-timestamp-millis\"}},\n" +
                "        {\"name\": \"localTsMicros\", \"type\": {\"type\":\"long\",\"logicalType\":\"local-timestamp-micros\"}}\n" +
                "    ]\n" +
                "}";
        Schema.Parser parser = new Schema.Parser();
        return parser.parse(balanceSchema);
    }

    private static IndexedRecord createBalanceRecord() {
        Schema schema = createBalanceSchema();
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("accountNumber", "0123456789");
        avroRecord.put("balance", new BigDecimal("10.00"));
        avroRecord.put("date", LocalDate.of(2021,1, 1));
        avroRecord.put("timeMs", LocalTime.of(1, 1, 1, 1000000));
        avroRecord.put("timeMicros", LocalTime.of(1, 1, 1, 1001000));
        avroRecord.put("tsMs", Instant.ofEpochMilli(1613646696368L));
        avroRecord.put("tsMicros", Instant.ofEpochMilli(1613646696368009L));
        avroRecord.put("localTsMs", LocalDateTime.of(2021,1, 1, 1, 1, 1, 1000000));
        avroRecord.put("localTsMicros", LocalDateTime.of(2021,1, 1, 1, 1, 1, 1001000));
        return avroRecord;
    }

    public static void main(final String[] args) {
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(KafkaAvroSerializerConfig.AVRO_USE_LOGICAL_TYPE_CONVERTERS_CONFIG, true);

        try (KafkaProducer<String, IndexedRecord> producer = new KafkaProducer<>(props)) {
            for (long i = 0; i < 10; i++) {
                producer.send(new ProducerRecord<>(TOPIC, createBalanceRecord()));
            }
            producer.flush();
        } catch (final SerializationException e) {
            e.printStackTrace();
        }

    }

}
