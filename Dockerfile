# alpine linux would be (a little bit) lighter than debian.
# For docker images, it's a go-to system (except if you do anything with C).
# For the second stage, I would stick with debian, as it's more intutive to administer.
FROM debian:bullseye-slim AS dqo-fetcher
ARG DQO_VERSION

# What is the purpose of wget here? If it's implicitly necessary or I overlooked something, tell me.
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
# 1. Using environment variable.
# 2. In order to extract the environment variable in quoted strings, it's better to use ${VAR} instead of $VAR.
CMD ["/bin/sh", "${DQO_HOME}/bin/dqo"]