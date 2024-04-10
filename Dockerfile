FROM openjdk:17-oracle

COPY target/*.jar expense.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","expense.jar"]