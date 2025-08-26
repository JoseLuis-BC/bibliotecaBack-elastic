# Etapa 1: Construcción con Maven y JDK 21 de Amazon Corretto
FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias primero (mejora la caché)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Amazon Corretto JDK 21
FROM amazoncorretto:21-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto (ajusta si tu app usa otro)
EXPOSE 8080

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]