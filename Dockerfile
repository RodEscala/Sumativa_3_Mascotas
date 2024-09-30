FROM eclipse-temurin:22-jdk AS buildstage 

RUN apt-get update && apt-get install -y maven

WORKDIR /app
# copia el pom y la carpeta src en el contenedor
COPY pom.xml .
COPY src /app/src
COPY Wallet_BDEJERCICIOS /app/wallet

ENV TNS_ADMIN=/app/wallet

RUN mvn clean package


FROM eclipse-temurin:22-jdk 

COPY --from=buildstage /app/target/masctoas-0.0.1-SNAPSHOT.jar /app/mascotas.jar

COPY Wallet_BDEJERCICIOS /app/wallet

ENTRYPOINT [ "java", "-jar","/app/mascotas.jar"]

