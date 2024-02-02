### Build stage
# Builder gradle
FROM gradle:jdk21-alpine AS builder

# Set the working directory inside the container
WORKDIR /

# Copy the source code into the container
COPY . .

# Build
RUN gradle clean backoffice-app:unpack -x test --no-daemon

# Unpack the jar
ARG UNPACKED=/backoffice-app/build/unpacked

# Copy unpacked into the container
COPY ${UNPACKED}/BOOT-INF/classes /upacked/app
COPY ${UNPACKED}/BOOT-INF/lib /upacked/app/lib
COPY ${UNPACKED}/META-INF /upacked/app/META-INF

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
