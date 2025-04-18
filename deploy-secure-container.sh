#!/bin/bash

# This script deploys the Minerva PoC to secure container instances
# Make sure to set up the necessary environment variables before running

# Required environment variables:
# - DOCKER_REGISTRY: Docker registry URL
# - IMAGE_NAME: Docker image name (default: minerva-poc)
# - IMAGE_TAG: Docker image tag (default: latest)
# - CONTAINER_INSTANCE_URL: The URL of your secure container instance service
# - AUTH_TOKEN: Authentication token for container service

# Exit on error
set -e

# Default values
IMAGE_NAME=${IMAGE_NAME:-"minerva-poc"}
IMAGE_TAG=${IMAGE_TAG:-"latest"}

# Check required environment variables
if [ -z "$DOCKER_REGISTRY" ]; then
  echo "Error: DOCKER_REGISTRY environment variable is not set"
  exit 1
fi

if [ -z "$CONTAINER_INSTANCE_URL" ]; then
  echo "Error: CONTAINER_INSTANCE_URL environment variable is not set"
  exit 1
fi

if [ -z "$AUTH_TOKEN" ]; then
  echo "Error: AUTH_TOKEN environment variable is not set"
  exit 1
fi

# Full image reference
FULL_IMAGE_NAME="${DOCKER_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}"

echo "Deploying ${FULL_IMAGE_NAME} to secure container instances..."

# Build the Docker image
echo "Building Docker image..."
docker build -t ${FULL_IMAGE_NAME} .

# Push to registry
echo "Pushing to registry..."
docker push ${FULL_IMAGE_NAME}

# Deploy to secure container instances
echo "Deploying to secure container instances..."
curl -X POST \
  -H "Authorization: Bearer ${AUTH_TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{
    "image": "'"${FULL_IMAGE_NAME}"'",
    "name": "'"${IMAGE_NAME}"'",
    "env": [
      {"name": "SPRING_PROFILES_ACTIVE", "value": "prod"},
      {"name": "SERVER_PORT", "value": "8080"}
    ],
    "ports": [
      {"port": 8080, "protocol": "TCP"}
    ]
  }' \
  ${CONTAINER_INSTANCE_URL}/instances

echo "Deployment complete!"
