# Dev container for Fact or Fake
# Base image with OpenJDK 17 and Maven
FROM mcr.microsoft.com/devcontainers/java:17

ENV DEBIAN_FRONTEND=noninteractive

# Install Maven, PostgreSQL client and useful tools
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       maven \
       postgresql-client \
       curl \
       git \
       nano \
       vim \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /workspaces

# Default to non-root user
USER vscode

