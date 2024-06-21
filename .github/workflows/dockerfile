# Verwenden eines Basis-Images mit Java-Unterstützung
FROM openjdk:11-jre-slim

# Installieren von Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Setzen des Arbeitsverzeichnisses im Container
WORKDIR /app

# Kopieren aller Anwendungen in das Arbeitsverzeichnis
COPY . .

# Erstellen der Anwendungen
WORKDIR /app/bank1
RUN mvn package -DskipTests

WORKDIR /app/bank2
RUN mvn package -DskipTests

WORKDIR /app/bank3
RUN mvn package -DskipTests

WORKDIR /app/bank4
RUN mvn package -DskipTests

WORKDIR /app/creditbureau
RUN mvn package -DskipTests

WORKDIR /app/loan-broker
RUN mvn package -DskipTests

# Zurück zum Hauptverzeichnis
WORKDIR /app

# Exponieren der Ports
EXPOSE 8080 8081 8082 8083 8084 8085 8086 8161 61616

# Startbefehl für alle Anwendungen
CMD ["sh", "-c", "\
    java -jar /app/bank1/target/<BANK1_JAR>.jar & \
    java -jar /app/bank2/target/<BANK2_JAR>.jar & \
    java -jar /app/bank3/target/<BANK3_JAR>.jar & \
    java -jar /app/bank4/target/<BANK4_JAR>.jar & \
    java -jar /app/creditbureau/target/<CREDITBUREAU_JAR>.jar & \
    java -jar /app/loan-broker/target/<LOANBROKER_JAR>.jar & \
    activemq start & \
    wait"
]
