FROM ibm-semeru-runtimes:open-18-jdk as builder

WORKDIR /usr/app

COPY . .

RUN ./gradlew installDist

FROM ibm-semeru-runtimes:open-18-jre

COPY --from=builder build/install/ktor-whoami ./

ENTRYPOINT ["bin/ktor-whoami"]
