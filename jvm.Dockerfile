### Build stage
# Builder gradle
FROM azul/zulu-openjdk-alpine:21 AS builder

# Set the working directory inside the container
WORKDIR /upacked

# Copy the source code into the container
COPY . .

# Build
RUN ./gradlew clean unpack -x test

# Unpack the jar
ARG UNPACKED=build/unpacked

COPY ${UNPACKED}/BOOT-INF/lib /upacked/lib
COPY ${UNPACKED}/META-INF /upacked/META-INF
COPY ${UNPACKED}/BOOT-INF/classes /upacked

### Run stage
# Create a minimal production image
FROM azul/zulu-openjdk-alpine:21-jre-headless

# Set the working directory inside the container
WORKDIR /

# Avoid running code as a root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy only the necessary dirs and files from the builder stage
COPY --from=builder /upacked ./app

# Run the binary when the container starts
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "dev.vlaship.backoffice.App"]
