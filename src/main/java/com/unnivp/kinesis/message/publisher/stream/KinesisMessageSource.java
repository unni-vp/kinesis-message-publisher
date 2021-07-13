package com.unnivp.kinesis.message.publisher.stream;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unnivp.kinesis.message.publisher.util.TelemetrySimulator;

/**
 * Class to generate a sample telemetry payload and publish it to the configured
 * Kinesis Data Stream at specified interval.
 * 
 * @author unnivp
 *
 */
@Component
@EnableBinding(Source.class)
public class KinesisMessageSource {

	private static final Logger logger = LoggerFactory.getLogger(KinesisMessageSource.class);

	@Value("#{'${telemetry.deviceIds}'.split(',')}")
	private List<String> deviceIdList;

	@Value("$(telemetry.deviceType)")
	private String deviceType;

	@Value("$(telemetry.interval)")
	private static final long TELEMETRY_INTERVAL = 10000L;

	@Autowired
	private Source source;
	
	
	/**
	 * Publish the payload to Kinesis data stream.
	 * @param payload the message to be published
	 * @return status
	 */
	public boolean produce(String payload) {
		
		logger.info("Publishing telemetry payload to Kinesis. Payload message : " + payload);
		return source.output().send(MessageBuilder.withPayload(payload).build());
	}

	@Scheduled(fixedRate = TELEMETRY_INTERVAL)
	private void produce() {

		logger.debug("Kinesis publisher execution Start");

		deviceIdList.forEach(deviceId -> {
			
			produce(TelemetrySimulator.getSimulatedTelemetryMessage(deviceId, deviceType));
		});

		logger.debug("Telemetry publisher execution End");
	}

}
