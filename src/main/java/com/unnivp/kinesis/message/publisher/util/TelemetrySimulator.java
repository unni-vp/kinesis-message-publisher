package com.unnivp.kinesis.message.publisher.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unnivp.kinesis.message.publisher.domain.TelemetryPayload;
import com.unnivp.kinesis.message.publisher.domain.TelemetryServiceRequest;
import com.unnivp.kinesis.message.publisher.domain.VTParam;

/**
 * The class that holds methods to generate a sample telemetry request.
 * 
 * @author unnivp
 * 
 */
public class TelemetrySimulator {

	private static final Logger logger = LoggerFactory.getLogger(TelemetrySimulator.class);

	/**
	 * Get a sample request message for sending device telemetry.
	 * 
	 * @param deviceId
	 *            device identifier for the payload
	 * @param deviceType
	 *            device type
	 * @return JSON string of the telemetry request
	 */
	public static String getSimulatedTelemetryMessage(String deviceId, String deviceType) {

		return JSONUtil.getJsonString(prepareTelemetryRequest(deviceId, deviceType));
	}

	/**
	 * Prepare sample request message for sending device telemetry.
	 * 
	 * @param deviceId
	 *            device identifier for the payload
	 * @param deviceType
	 *            device type
	 * @return request object containing telemetry payload
	 */
	private static TelemetryServiceRequest prepareTelemetryRequest(String deviceId, String deviceType) {

		TelemetryServiceRequest message = new TelemetryServiceRequest();
		TelemetryPayload records = new TelemetryPayload();
		records.setDeviceId(deviceId);
		records.setDeviceType(deviceType);

		List<VTParam> parameters = new ArrayList<>();
		Map<String, Double> telemeteyParamMap = getTelemetyAttributeMap();
		Calendar timestamp = Calendar.getInstance();

		// Generate telemetry data based on random values and add them to the payload.
		telemeteyParamMap.forEach((param, value) -> {
			VTParam parameter = new VTParam();
			parameter.setTimestamp(timestamp.getTime());
			parameter.setParameter(param);
			parameter.setValue(getRandomDouble(value, 4, 2));
			parameters.add(parameter);
		});
		records.setTelemetryParameters(parameters);
		message.setPayload(records);

		logger.debug("Telemetry data added for " + deviceId + " : " + timestamp.getTime());
		return message;
	}

	/**
	 * Generates a random double based on value, range and scale. eg: @value =
	 * 10, @range = 10, @scale = 1, will generate random values between 5.0 and 15.0
	 * 
	 * @param value
	 *            value based on which random double to be generated
	 * @param range
	 *            range to be applied to the input double value to generate random
	 *            double
	 * @param scale
	 *            scale of the double value to be returned
	 * @return random double value based on input value, range and scale
	 */
	private static double getRandomDouble(double value, int range, int scale) {

		Random random = new Random();
		double randomValue = (value - range / 2) + (random.nextDouble() * (range));
		BigDecimal decimalValue = new BigDecimal(randomValue).setScale(scale, RoundingMode.HALF_UP);
		return decimalValue.doubleValue();
	}

	/**
	 * Define a map of telemetry attributes with their median values. These sample
	 * values will be used to generate the telemetry payload.
	 * 
	 * @return telemetry parameter map
	 */
	private static Map<String, Double> getTelemetyAttributeMap() {

		Map<String, Double> telemetyAttributeMap = new HashMap<>();

		telemetyAttributeMap.put("temperature", -79d);
		telemetyAttributeMap.put("humidity", 24d);

		return telemetyAttributeMap;
	}
}