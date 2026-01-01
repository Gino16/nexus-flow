package com.nexusflow.analytics.consumer;

import io.smallrye.common.annotation.RunOnVirtualThread;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@Slf4j
public class TelemetryConsumer {

  @Incoming(value = "telemetry-in")
  @RunOnVirtualThread
  public void processMessage(JsonObject message) {
    try {
      JsonObject data = message.getJsonObject("data");
      String deviceId = message.getString("deviceId");
      double temp = data.getDouble("temperature");

      log.info("Processed telemetry from device {}: temperature = {}", deviceId, temp);

      if (temp > 30) {
        log.warn("Temperature is too high: {}", temp);
      }

    } catch (Exception e) {
      log.error("Error processing telemetry message", e);
    }
  }
}
