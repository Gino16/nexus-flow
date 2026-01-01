package com.nexusflow.ingestion.repository;

import io.smallrye.mutiny.Uni;
import com.nexusflow.ingestion.model.TelemetryEvent;

public interface TelemetryRepository {
  Uni<TelemetryEvent> save(TelemetryEvent event);
}
