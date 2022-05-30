FROM ibm-semeru-runtimes:open-18-jdk as builder

COPY . .

RUN ./gradlew installDist

FROM ibm-semeru-runtimes:open-18-jre

WORKDIR /usr/app

COPY --from=builder build/install/ktor-whoami ./

ENTRYPOINT ["bin/ktor-whoami"]
