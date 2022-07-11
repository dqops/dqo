FROM python:3.7-slim-bullseye
WORKDIR /app

RUN apt-get update \
    && apt-get install unzip \
    && apt-get install wget -y \
    && apt-get clean


# install java
RUN apt-get install -y openjdk-17-jre && apt-get clean
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# dqo
ARG DQO_VERSION

RUN --mount=type=bind,target=/app/temp/,source=./distribution/target/ \
    unzip /app/temp/dqo-distribution-$DQO_VERSION-bin.zip -d /app/dqo-$DQO_VERSION

WORKDIR /app/userhome

ENV DQO_HOME=/app/dqo-$DQO_VERSION
ENV DQO_USER_HOME=/app/userhome

RUN chsh -s /bin/sh
CMD ["/bin/sh", "/app/dqo-0.1.0/bin/dqo"]