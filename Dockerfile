FROM amazoncorretto:17
COPY target/*.jar offer-mgmt-ms-0.0.1.jar
EXPOSE 8087
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar", "offer-mgmt-ms-0.0.1.jar"]
ENV SPRING_CONFIG_LOCATION=file:/app/config/
