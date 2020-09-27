FROM openjdk:14-alpine
COPY target/task3-*.jar task3.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "task3.jar"]