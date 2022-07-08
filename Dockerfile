FROM python:3.7-bullseye
WORKDIR /app

RUN apt-get update
RUN apt-get install unzip
RUN apt-get install wget -y


# install java
RUN apt-get install -y openjdk-17-jdk && apt-get clean;
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin

# dqo
ARG DQO_VERSION

COPY ./distribution/target/dqo-distribution-$DQO_VERSION-bin.zip /app/temp/dqo-distribution-$DQO_VERSION-bin.zip
RUN unzip /app/temp/dqo-distribution-$DQO_VERSION-bin.zip -d /app/dqo-$DQO_VERSION
RUN rm -rf /app/temp

COPY ./userhome /app/userhome
RUN cd /app/userhome

ENV DQO_HOME=/app/dqo-$DQO_VERSION
ENV DQO_USER_HOME=/app/userhome


#RUN apt-get install nano
RUN chsh -s /bin/sh

CMD ["/bin/sh", "/app/dqo-0.1.0/bin/dqo"]