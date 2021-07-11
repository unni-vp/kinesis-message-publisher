package com.unnivp.kinesis.message.publisher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.unnivp.kinesis.message.publisher.stream.KinesisMessageSource;
import com.unnivp.kinesis.message.publisher.util.TelemetrySimulator;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {
	
	@Autowired
	private KinesisMessageSource kinesisMessageSource;
	
	@Test
	public void testKinesisPublisher_Success() {
		
		boolean status = kinesisMessageSource.produce(TelemetrySimulator.getSimulatedTelemetryMessage("Test123", "TestDevice"));
		
		assertTrue("Kinesis publishing failed", status);
	}
	
	@Test
	public void testKinesisPublisher_ThrowsException() {
		
	    Throwable exception = assertThrows(IllegalArgumentException.class, () -> kinesisMessageSource.produce(null));
	    assertEquals("Payload must not be null", exception.getMessage());
	}
}
