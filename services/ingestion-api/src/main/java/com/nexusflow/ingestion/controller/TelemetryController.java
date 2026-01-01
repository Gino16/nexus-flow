package com.nexusflow.ingestion.controller;

import com.nexusflow.ingestion.generated.api.TelemetraApi;
import com.nexusflow.ingestion.generated.model.TelemetryRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.util.concurrent.CompletionStage;
import com.nexusflow.ingestion.service.TelemetryService;

@ApplicationScoped
public class TelemetryController implements TelemetraApi {

  @Inject
  TelemetryService telemetryService;

  @Override
  public CompletionStage<Response> ingestTelemetry(TelemetryRequest telemetryRequest) {
    return telemetryService.ingestTelemetry(telemetryRequest)
        .replaceWith(Response.accepted().build())
        .subscribeAsCompletionStage();
  }
}
