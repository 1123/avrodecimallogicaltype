# Dealing with Avro Decimal Values and Confluent Platform

This repository gives best practices for dealing with Decimal values in Confluent Platform, when writing the Data to Kafka in Avro format. 
It includes the following examples: 

* Generating Java classes from an Avro schema containing a field of type decimal using a maven plugin
* Using the generated classes for producing data using the Specific Avro format
* Reading the generated data in Specific Avro format and deserializing it to Java objects
* Writing and reading data with decimal field values using Generic Avro format
* Exporting the data using a JDBC sink connector to an Sqlite database
* Exporting the data using a JDBC sink connector a SAP HANA. 

# Prerequisites

* Java Development Kit 8 or newer
* A recent version of Apache maven installed
* Internet Conncectivity
* Linux or MacOS operating system
* sqlite for running the demo with SQLite
* docker for running the demo with SAP HANA. Also 8 GB of RAM for the docker Hana container. 

# Running the demo

* Download Confluent Platform (tested with CP 7.0.1)
* Inspect the code in the `src` directory. 
  It shows how to write data in the specific avro format an in the generic avro format to Kafka. 
* Set the `CONFLUENT_HOME` variable
* install the Confluent JDBC Connector: `./install-jdbc-connector.sh`
* install the JDBC driver for HANA: `get-the-ngdbc-driver.sh`
* start Confluent Platform : `confluent local services start`
* run the producer: run-specific-producer.sh
* run the consumer: run-specific-consumer.sh (you should see some data being consumed from Kafka)
* deploy the jdbc sink connector for SQLITE: `deploy-transactions-connector.sh`
* deploy the jdbc sink connector for SAP HANA: `deploy-transactions-connector-hana.sh`

