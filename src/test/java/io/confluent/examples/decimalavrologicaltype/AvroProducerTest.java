package io.confluent.examples.decimalavrologicaltype;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class AvroProducerTest {

    @Test
    public void testPaymentAvro2() throws IOException {
        Conversions.DecimalConversion decimalConverter = new Conversions.DecimalConversion();
        Payment test2 = Payment.newBuilder().setId("ssdfd").setAmount(10.3).setDecimalAmount(null).build();
        test2.setDecimalAmount(decimalConverter.toBytes(new BigDecimal("1.450000").setScale(9),null, LogicalTypes.decimal(30,9)));

        final DatumWriter<Payment> datumWriterPayment = new SpecificDatumWriter<>(Payment.class);
        try (DataFileWriter<Payment> dataFileWriter = new DataFileWriter<>(datumWriterPayment)) {
            dataFileWriter.create(test2.getSchema(), new File("paymentavro"));
            dataFileWriter.append(test2);
        } catch (Exception e){
            e.printStackTrace();
        }

        // read it from a file
        final File file = new File("paymentavro");
        final DatumReader<Payment> datumReader = new SpecificDatumReader<>(Payment.class);
        final DataFileReader<Payment> dataFileReader;

        dataFileReader = new DataFileReader<>(file, datumReader);
        while (dataFileReader.hasNext()) {
            Payment payment = dataFileReader.next();
            Assertions.assertNull(payment.getDecimalAmount());
            //Assertions.assertEquals(null,new Conversions.DecimalConversion().fromBytes(payment.getDecimalAmount(),payment.getSchema().getField("decimalAmount").schema(),
            //        LogicalTypes.decimal(30,9)));
            Assertions.assertEquals("ssdfd",payment.getId().toString());
            Assertions.assertEquals(10.3d,payment.getAmount());
        }
        if(dataFileReader != null)
            dataFileReader.close();
        Assertions.assertTrue(file.delete());
    }


    @Test
    public void testPaymentAvro3() throws IOException {
        Payment test2 = Payment.newBuilder().setId("ssdfd").setAmount(10.3).setDecimalAmount(null).build();
        final DatumWriter<Payment> datumWriterPayment = new SpecificDatumWriter<>(Payment.class);
        try (DataFileWriter<Payment> dataFileWriter = new DataFileWriter<>(datumWriterPayment)) {
            dataFileWriter.create(test2.getSchema(), new File("paymentavro"));
            dataFileWriter.append(test2);
        } catch (Exception e){
            e.printStackTrace();
        }

        // read it from a file
        final File file = new File("paymentavro");
        final DatumReader<Payment> datumReader = new SpecificDatumReader<>(Payment.class);
        final DataFileReader<Payment> dataFileReader;

        dataFileReader = new DataFileReader<>(file, datumReader);
        while (dataFileReader.hasNext()) {
            Payment payment = dataFileReader.next();
            Assertions.assertNull(payment.getDecimalAmount());
            //Assertions.assertEquals(null,new Conversions.DecimalConversion().fromBytes(payment.getDecimalAmount(),payment.getSchema().getField("decimalAmount").schema(),
            //        LogicalTypes.decimal(30,9)));
            Assertions.assertEquals("ssdfd",payment.getId().toString());
            Assertions.assertEquals(10.3d,payment.getAmount());
        }
        if(dataFileReader != null)
            dataFileReader.close();
        Assertions.assertTrue(file.delete());
    }

    @Test
    public void testPaymentAvro4() throws IOException {
        Payment test2 = new Payment();
        test2.setAmount(10.3);
        test2.setId("test2");
        test2.setDecimalAmount(null);
        final DatumWriter<Payment> datumWriterPayment = new SpecificDatumWriter<>(Payment.class);
        try (DataFileWriter<Payment> dataFileWriter = new DataFileWriter<>(datumWriterPayment)) {
            dataFileWriter.create(test2.getSchema(), new File("paymentavro"));
            dataFileWriter.append(test2);
        } catch (Exception e){
            e.printStackTrace();
        }

        // read it from a file
        final File file = new File("paymentavro");
        final DatumReader<Payment> datumReader = new SpecificDatumReader<>(Payment.class);
        final DataFileReader<Payment> dataFileReader;

        dataFileReader = new DataFileReader<>(file, datumReader);
        while (dataFileReader.hasNext()) {
            Payment payment = dataFileReader.next();
            Assertions.assertNull(payment.getDecimalAmount());
            //Assertions.assertEquals(null,new Conversions.DecimalConversion().fromBytes(payment.getDecimalAmount(),payment.getSchema().getField("decimalAmount").schema(), LogicalTypes
            //        .decimal(30,9)));
            Assertions.assertEquals("test2",payment.getId().toString());
            Assertions.assertEquals(10.3d,payment.getAmount());
        }
        if(dataFileReader != null)
            dataFileReader.close();
        Assertions.assertTrue(file.delete());
    }


}
