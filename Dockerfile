FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build do projeto
RUN mvn clean package -DskipTests

# Imagem final
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copiar JAR da etapa de build
COPY --from=build /app/target/gamification.jar app.jar

# Expor porta
EXPOSE 3000

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
