### Build stage
# Builder
FROM alpine:latest AS builder

# Set the working directory inside the container
WORKDIR /

# Copy unpacked into the container
COPY . .

# Unpack the jar
ARG UNPACKED=/backoffice-app/build/unpacked

COPY ${UNPACKED}/BOOT-INF/lib /upacked/app/lib
COPY ${UNPACKED}/META-INF /upacked/app/META-INF
COPY ${UNPACKED}/BOOT-INF/classes /upacked/app

### Run stage
# Create a minimal production image
FROM azul/zulu-openjdk-alpine:21-jre-headless

# Set the working directory inside the container
WORKDIR /app

# Avoid running code as a root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy only the necessary dirs and files from the builder stage
COPY --from=builder /upacked/app /app

# Run the binary when the container starts
ENTRYPOINT ["java", "-cp", "/app:/app/lib/*", "dev.vlaship.backoffice.App"]
