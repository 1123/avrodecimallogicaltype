# Dealing with Avro Decimal Values and Confluent Platform

This repository gives best practices for dealing with Decimal values in Confluent Platform, when writing the Data to Kafka in Avro format. 
It includes the following examples: 

* Generating Java classes from an Avro schema containing a field of type decimal using a maven plugin
* Using the generated classes for producing data using the Specific Avro format
* Reading the generated data in Specific Avro format and deserializing it to Java objects
* Writing and reading data with decimal field values using Generic Avro format
* Exporting the data using a JDBC sink connector to an Sqlite database
