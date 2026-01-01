package com.nexusflow.ingestion.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexusflow.ingestion.generated.model.TelemetryRequest;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import com.nexusflow.ingestion.model.TelemetryEvent;
import com.nexusflow.ingestion.repository.TelemetryRepository;
import com.nexusflow.ingestion.service.TelemetryService;

@ApplicationScoped
@Slf4j
public class TelemetryServiceImpl implements TelemetryService {

  @Inject
  TelemetryRepository telemetryRepository;

  @Inject
  @Channel("telemetry-out")
  Emitter<JsonObject> kafkaEmitter;

  @Override
  public Uni<Void> ingestTelemetry(TelemetryRequest telemetryRequest) {
    TelemetryEvent event = new TelemetryEvent();
    event.id = UUID.fromString(telemetryRequest.getMetadata().getId());
    event.deviceId = telemetryRequest.getDeviceId();
    event.payload = telemetryRequest.getData().toString();
    event.createdAt = LocalDate.ofInstant(telemetryRequest.getMetadata().getTime().toInstant(),
        ZoneId.systemDefault()).atStartOfDay();
    event.status = "RECEIVED";
    ObjectMapper objectMapper = new ObjectMapper();
    return telemetryRepository.save(event)
        .onItem()
        .transformToUni(savedEvent -> {
          log.info("Received telemetry event");
          JsonObject kafkaMessage = JsonObject.mapFrom(telemetryRequest);
          kafkaEmitter.send(kafkaMessage);
          log.info("Telemetry event sent to Kafka");
          return Uni.createFrom().voidItem();
        })
        .onFailure()
        .invoke(throwable -> log.error("Error saving telemetry event", throwable));
  }
}
