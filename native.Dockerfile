### Build stage
# Builder
FROM ghcr.io/graalvm/native-image-community:21-ol9 AS builder

# Install the tools
RUN microdnf install findutils

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build the binary
RUN ./gradlew clean :backoffice-app:nativeCompile -x test

### Run stage
# Create a minimal production image
FROM oraclelinux:9-slim

# Set the working directory inside the container
WORKDIR /app

# Avoid running code as a root user
RUN useradd -ms /bin/bash appuser
USER appuser

# Copy only the necessary files from the builder stage
COPY --from=builder /app/backoffice-app/build/native/nativeCompile/app .

# Run the binary when the container starts
CMD ["./app"]
