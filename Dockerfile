FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=target/simian-project-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

ENV SPRING_DATASOURCE_URL=jdbc:sqlserver://simianbdserver.database.windows.net;databaseName=simianbd
ENV SPRING_DATASOURCE_USERNAME=simianadmin
ENV SPRING_DATASOURCE_PASSWORD=m3uSimian

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 80