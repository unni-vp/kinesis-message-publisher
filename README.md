# kinesis-message-publisher

Utility Project to generate sample telemetry messages and publish it to a Kinesis Data Stream at specified interval, using 'Spring Cloud Stream Binder Kinesis' library.

## Installation
Use maven to compile and build project:
```bash
mvn clean install 
```
Run the spring-boot application:
```bash
mvn spring-boot:run
```

## Configuration

Modify the 'application.yml' file for specifying the below:
 - deviceIds : Specify the identifiers for devices for which the telemtry messages need to be simulated. Mutliple device identifiers can be specified, seperated by commas. Message payload will contain this identifier.
 - deviceType : Specify the type of device. Each message payload will contain this device type.
 - interval : The interval, in milliseconds, at which the messages are sent for each device.
