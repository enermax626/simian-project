FROM adoptopenjdk:11-jre-hotspot

ARG JAR_FILE=target/simian-project-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

#ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
#ENV SPRING_DATASOURCE_USERNAME=sa
#ENV SPRING_DATASOURCE_PASSWORD=sa

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 80