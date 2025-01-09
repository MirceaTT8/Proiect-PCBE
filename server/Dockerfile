FROM maven:3.9-amazoncorretto-23 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY src/main/resources/stockexchange.p12 /app/stockexchange.p12
RUN mvn clean package -DskipTests

FROM amazoncorretto:23
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh .
RUN chmod +x wait-for-it.sh

EXPOSE 8443
ENTRYPOINT ["./wait-for-it.sh", "postgres:5432", "--timeout=30", "--strict", "--", \
           "./wait-for-it.sh", "rabbitmq:5672", "--timeout=30", "--strict", "--", \
           "java", "-jar", "app.jar"]
