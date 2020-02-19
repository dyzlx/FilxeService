FROM java:8
MAINTAINER duyunzelx@outlook.com
ADD filxeservice.jar /app.jar
EXPOSE 8881
ENTRYPOINT ["/usr/bin/java", "-jar", "app.jar"]