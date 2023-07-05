#stage1
FROM registry.access.redhat.com/ubi8/openjdk-11:1.15-1.1679485219 as builder
COPY . .
RUN mvn clean package

#stage2
FROM registry.access.redhat.com/ubi8/openjdk-11-runtime:1.15-1.1679485252

ENV JAR_NAME=camel-batch-sample-2.5-SNAPSHOT

COPY . .
COPY --from=builder /home/jboss/target .
ENV JAR_FILE=/home/jboss/${JAR_NAME}.jar
RUN cp ${JAR_FILE} /home/jboss/app.jar
EXPOSE 8080 8081
ENTRYPOINT ["java","-jar","/home/jboss/app.jar","-Djava.net.preferIPv4Stack=true -Dspring.cloud.kubernetes.enabled=false"]

