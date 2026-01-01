package com.nexusflow.ingestion.repository.impl;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import com.nexusflow.ingestion.model.TelemetryEvent;
import com.nexusflow.ingestion.repository.TelemetryRepository;

@ApplicationScoped
public class TelemetryRepositoryImpl implements TelemetryRepository {

  @Override
  public Uni<TelemetryEvent> save(TelemetryEvent event) {
    return Panache.withTransaction(event::persist);
  }
}
