package com.nexusflow.ingestion.service;

import com.nexusflow.ingestion.generated.model.TelemetryRequest;
import io.smallrye.mutiny.Uni;

public interface TelemetryService {

  Uni<Void> ingestTelemetry(TelemetryRequest telemetryRequest);
}
