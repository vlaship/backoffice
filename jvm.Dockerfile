### Build stage
# Builder gradle
FROM gradle:jdk21-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the source code into the container
COPY . .

# Build
RUN gradle clean unpack -x test

# Unpack the jar
ARG UNPACKED=build/unpacked
COPY ${UNPACKED}/BOOT-INF/lib /app/upacked/lib
COPY ${UNPACKED}/META-INF /app/upacked/META-INF
COPY ${UNPACKED}/BOOT-INF/classes /app/upacked

### Run stage
# Create a minimal production image
FROM azul/zulu-openjdk-alpine:21-jre-headless

# Set the working directory inside the container
WORKDIR /upacked

# Avoid running code as a root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy only the necessary dirs and files from the builder stage
COPY --from=builder /app/upacked /upacked/app

# Run the binary when the container starts
CMD ["java", "-cp", "app:app/lib/*", "vlaship.backoffice.App"]
