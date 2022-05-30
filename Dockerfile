FROM gradle:7.4.2-jdk18-alpine as builder

WORKDIR /usr/app

COPY . .

RUN ./gradlew installDist

FROM ibm-semeru-runtimes:open-18-jre

COPY --from=builder build/install/ktor-whoami ./

ENTRYPOINT ["bin/ktor-whoami"]
