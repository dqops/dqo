FROM openjdk:17-jdk-slim-buster AS dqo-libs
WORKDIR /workspace/app

COPY --chmod=755 mvnw.sh ./
COPY .mvn .mvn

COPY pom.xml .
COPY dqops/pom.xml dqops/
COPY distribution/pom.xml distribution/
COPY lib lib
COPY VERSION VERSION

# resolve dependencies
ENV USER_HOME="/user/mvn"
RUN mkdir -p "${USER_HOME}/.m2" && ./mvnw.sh dependency:go-offline -Prun-npm
RUN ./mvnw.sh frontend:install-node-and-npm -pl dqops

# npm install
COPY dqops/src/main/frontend/package.json dqops/src/main/frontend/
ENV PATH="/workspace/app/dqops/src/main/frontend/node:${PATH}"
RUN ./mvnw.sh frontend:npm@npm-install -pl dqops -Prun-npm

WORKDIR /workspace/app

# compile project
COPY dqops/src dqops/src
RUN ./mvnw.sh install -DskipTests -pl !distribution
RUN mkdir -p dqops/target/dependency && (cd dqops/target/dependency; jar -xf ../*.jar)
RUN mv lib/target/output/dqo-lib-$(cat /workspace/app/VERSION) lib/target/output/dqo-lib

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
RUN apt-get update && apt-get install -y openjdk-17-jre-headless && apt-get clean
ARG TARGETARCH=amd64
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-$TARGETARCH
ENV PATH=$JAVA_HOME/bin:$PATH

# dqo launch setup
ENV DQO_HOME=/dqo/home
ENV DQO_USER_HOME=/dqo/userhome
ENV DQO_USER_INITIALIZE_USER_HOME=true
ENV DQO_PYTHON_USE_HOST_PYTHON=true
ENV DQO_LOGGING_CONSOLE_IMMEDIATE_FLUSH=true
ENV GOOGLE_APPLICATION_CREDENTIALS=$DQO_USER_HOME/.credentials/GCP_application_default_credentials.json
RUN mkdir $DQO_USER_HOME
RUN touch $DQO_USER_HOME/.DQO_USER_HOME_NOT_MOUNTED
COPY --from=dqo-home /dqo/home home

# copy spring dependencies
ARG DEPENDENCY=/workspace/app/dqops/target/dependency
COPY --from=dqo-libs /workspace/app/lib/target/output/dqo-lib/jars /dqo/app/lib
COPY --from=dqo-libs ${DEPENDENCY}/BOOT-INF/lib /dqo/app/lib
COPY --from=dqo-libs ${DEPENDENCY}/META-INF /dqo/app/META-INF
COPY --from=dqo-libs ${DEPENDENCY}/BOOT-INF/classes /dqo/app
ENV JAVA_TOOL_OPTIONS="-Xmx1024m"
ENTRYPOINT ["java", "-cp", "/dqo/app:/dqo/app/lib/*", "com.dqops.cli.CliApplication"]
