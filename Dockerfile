FROM debian:bullseye-slim AS dqo-fetcher
ARG DQO_VERSION

RUN apt-get update \
    && apt-get install unzip \
    && apt-get install wget -y \
    && apt-get clean

WORKDIR /app
COPY ./distribution/target/ /app/temp/
RUN unzip /app/temp/dqo-distribution-$DQO_VERSION-bin.zip -d /app/dqo-$DQO_VERSION


FROM python:3.7-slim-bullseye AS dqo-main
ARG DQO_VERSION
WORKDIR /app

# install java
RUN apt-get update && apt-get install -y openjdk-17-jre && apt-get clean
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# dqo
COPY --from=dqo-fetcher /app/dqo-$DQO_VERSION /app/dqo-$DQO_VERSION
WORKDIR /app/userhome
ENV DQO_HOME=/app/dqo-$DQO_VERSION
ENV DQO_USER_HOME=/app/userhome

RUN chsh -s /bin/sh
CMD ["/bin/sh", "/app/dqo-$DQO_VERSION/bin/dqo"]