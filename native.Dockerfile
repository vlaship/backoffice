### Build stage
# Builder
FROM ghcr.io/graalvm/native-image-community:21-ol9 AS builder

# Install the tools
RUN microdnf install findutils

# Set the working directory inside the container
WORKDIR /tmp

# Copy the source code into the container
COPY . .

# Make the Gradle wrapper script executable
RUN chmod +x gradlew

# Build the binary
RUN ./gradlew clean :backoffice-app:nativeCompile -x test

### Run stage
# Create a minimal production image
FROM oraclelinux:9-slim

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files from the builder stage
COPY --from=builder /tmp/backoffice-app/build/native/nativeCompile/backoffice-app .

# Ensure the binary has execution permissions
RUN chmod +x ./backoffice-app

# Avoid running code as a root user
RUN useradd -ms /bin/bash appuser
USER appuser

# Run the binary when the container starts
CMD ["./backoffice-app"]
