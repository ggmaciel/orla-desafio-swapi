FROM eclipse-temurin:21-alpine

ADD target/*.jar /opt/app.jar
CMD ["java", "-jar", "/opt/app.jar"]
