FROM java:8-jdk-alpine

COPY ./target/et-orders-1.0.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch et-orders-1.0.0.jar'

ENTRYPOINT ["java","-jar","et-orders-1.0.0.jar"]