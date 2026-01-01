#!/bin/bash

# Configuración de variables
CONTAINER_NAME="nexus-auth"
ADMIN_USER="admin"
ADMIN_PASS="admin"
REALM_NAME="NexusFlow"
CLIENT_ID="ingestion-api"

echo "Iniciando configuración de Keycloak en el contenedor $CONTAINER_NAME..."

# 1. Autenticación inicial
docker exec -it $CONTAINER_NAME /opt/keycloak/bin/kcadm.sh config credentials \
  --server http://localhost:8080 \
  --realm master \
  --user $ADMIN_USER \
  --password $ADMIN_PASS

# 2. Crear el Realm
echo "Creando Realm: $REALM_NAME..."
docker exec -it $CONTAINER_NAME /opt/keycloak/bin/kcadm.sh create realms -s realm=$REALM_NAME -s enabled=true

# 3. Crear el Cliente para el microservicio de Ingesta
echo "Creando Cliente: $CLIENT_ID..."
docker exec -it $CONTAINER_NAME /opt/keycloak/bin/kcadm.sh create clients \
  -r $REALM_NAME \
  -s clientId=$CLIENT_ID \
  -s enabled=true \
  -s serviceAccountsEnabled=true \
  -s publicClient=false \
  -s secret=nexus-flow-very-secret-key-123

# 4. Asignar un rol de ejemplo (opcional pero recomendado)
echo "Creando rol 'telemetry-write'..."
docker exec -it $CONTAINER_NAME /opt/keycloak/bin/kcadm.sh create roles -r $REALM_NAME -s name=telemetry-write

echo "----------------------------------------------------------"
echo "Configuración completada con éxito."
echo "Usa este Secret en tu application.yml:"
echo "quarkus.oidc.credentials.secret: nexus-flow-very-secret-key-123"
echo "----------------------------------------------------------"