FROM alpine:3.6
RUN apk --update add openjdk8-jre

COPY start-service.sh ./
COPY lancio-billing-0.0.1-SNAPSHOT.jar ./
COPY lancio-hazelcast-client.xml /configurations/hazelcast/

EXPOSE 8082 5701 5702 5703
ENTRYPOINT ["./start-service.sh"]
