FROM openjdk:17-jdk-slim-buster AS dqo-libs
WORKDIR /workspace/app

COPY --chmod=755 mvnw.sh ./
COPY .mvn .mvn

COPY pom.xml .
COPY dqops/pom.xml dqops/
COPY distribution/pom.xml distribution/
COPY lib lib

# resolve dependencies
ENV USER_HOME="/user/mvn"
RUN mkdir -p "${USER_HOME}/.m2" && ./mvnw.sh dependency:resolve -pl !distribution && ./mvnw.sh dependency:resolve-plugins -pl !distribution
RUN ./mvnw.sh frontend:install-node-and-npm -pl dqops

# npm install
COPY dqops/src/main/frontend/package.json dqops/src/main/frontend/
ENV PATH="/workspace/app/dqops/src/main/frontend/node:${PATH}"
WORKDIR /workspace/app/dqops/src/main/frontend
RUN npm install --legacy-peer-deps

WORKDIR /workspace/app

# compile project
COPY dqops/src dqops/src
RUN ./mvnw.sh install -DskipTests -pl !distribution
RUN mkdir -p dqops/target/dependency && (cd dqops/target/dependency; jar -xf ../*.jar)

FROM python:3.11.3-slim-bullseye AS dqo-home
WORKDIR /dqo

# copy dqo home
COPY home home
WORKDIR /dqo/home
RUN rm -rf venv/

# recreate venv
ENV VIRTUAL_ENV=/dqo/home/venv
RUN python3 -m venv $VIRTUAL_ENV
ENV PATH="$VIRTUAL_ENV/bin:$PATH"
RUN cp lib/requirements.txt $VIRTUAL_ENV/home_requirements.txt
RUN pip3 install -r $VIRTUAL_ENV/home_requirements.txt

RUN python3 -m compileall ./

FROM python:3.11.3-slim-bullseye AS dqo-main
EXPOSE 8888
WORKDIR /dqo

# install java
RUN apt-get update && apt-get install -y ca-certificates-java openjdk-17-jre && apt-get clean
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-$TARGETARCH
ENV PATH=$PATH:$JAVA_HOME/bin

# dqo launch setup
ENV DQO_HOME=/dqo/home
ENV DQO_USER_HOME=/dqo/userhome
ENV DQO_USER_INITIALIZE_USER_HOME=true
RUN mkdir $DQO_USER_HOME
RUN touch $DQO_USER_HOME/.DQO_USER_HOME_NOT_MOUNTED
COPY --from=dqo-home /dqo/home home

# copy spring dependencies
ARG DEPENDENCY=/workspace/app/dqops/target/dependency
COPY --from=dqo-libs ${DEPENDENCY}/BOOT-INF/lib /dqo/lib
COPY --from=dqo-libs ${DEPENDENCY}/META-INF /dqo/META-INF
COPY --from=dqo-libs ${DEPENDENCY}/BOOT-INF/classes /dqo
ENV JAVA_TOOL_OPTIONS="-Xmx1024m"
ENTRYPOINT ["java", "-cp", "/dqo:/dqo/lib/*", "com.dqops.cli.CliApplication"]
