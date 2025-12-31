package org.nexusflow.ingestion.service;

import com.nexusflow.ingestion.model.TelemetryRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface TelemetryService {
  Uni<Response> ingestTelemetry(TelemetryRequest telemetryRequest);
}
