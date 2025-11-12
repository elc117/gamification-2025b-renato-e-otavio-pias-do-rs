# Dev container for Fact or Fake
# Base image with OpenJDK and common tooling (Maven preinstalled)
FROM mcr.microsoft.com/vscode/devcontainers/java:0-17

ENV DEBIAN_FRONTEND=noninteractive

# Install PostgreSQL client and small useful tools
RUN apt-get update \
    && apt-get install -y --no-install-recommends postgresql-client curl git \
    && rm -rf /var/lib/apt/lists/*

# Set working directory (this will be mounted to your workspace)
WORKDIR /workspace

# Default to non-root 'vscode' user provided by the base image
USER vscode

