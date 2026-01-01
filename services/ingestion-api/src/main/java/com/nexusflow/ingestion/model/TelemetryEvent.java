package com.nexusflow.ingestion.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "telemetry_event")
public class TelemetryEvent extends PanacheEntityBase {

  @Id
  public UUID id;

  @Column(name = "device_id", nullable = false)
  public UUID deviceId;

  @Column(columnDefinition = "jsonb")
  public String payload;

  public LocalDateTime createdAt;

  public String status;
}
