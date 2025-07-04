# Etapa 1: builder con JDK 24 + Maven
FROM eclipse-temurin:24-jdk AS builder
WORKDIR /app

RUN apt-get update \
    && apt-get install -y maven \
    && rm -rf /var/lib/apt/lists/*

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: runtime con JRE
FROM eclipse-temurin:24-jre AS runtime
WORKDIR /app

ENV PORT=8080
EXPOSE 8080

COPY --from=builder /app/target/ms-books-catalogue-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true","-jar","/app/app.jar"]
