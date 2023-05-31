FROM alpine:3.17.0 AS dqo-fetcher
RUN apk update && apk add unzip

WORKDIR /dqo
COPY ./distribution/target/ /dqo/temp/
COPY ./VERSION /dqo/temp/
RUN unzip /dqo/temp/dqo-distribution-$(cat /dqo/temp/VERSION)-bin.zip -d /dqo/home


FROM python:3.10.8-slim-bullseye AS dqo-main
EXPOSE 8888
WORKDIR /dqo

# install java
RUN apt-get update && apt-get install -y openjdk-17-jre && apt-get clean
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# dqo
COPY --from=dqo-fetcher /dqo/home /dqo/home
WORKDIR /dqo/userhome
RUN touch .DQO_USER_HOME_NOT_MOUNTED
ENV DQO_HOME=/dqo/home
ENV DQO_USER_HOME=/dqo/userhome
ENV DQO_USER_INITIALIZE_USER_HOME=true

ENTRYPOINT ["/bin/sh", "/dqo/home/bin/dqo"]
