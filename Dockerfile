FROM eclipse-temurin:21-jdk
COPY target/medicalclinic-proxy-0.0.1-SNAPSHOT.jar appMedicalclinicProxy/appMedicalclinicProxy.jar
ENTRYPOINT ["java", "-jar", "appMedicalclinicProxy/appMedicalclinicProxy.jar"]