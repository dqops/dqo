FROM eclipse-temurin:11-jre-alpine

WORKDIR /spark

ENV SPARK_VERSION 3.2.1
ENV HADOOP_VERSION 3.2

ENV SPARK_ARCHIVE spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}.tgz

RUN wget "https://archive.apache.org/dist/spark/spark-${SPARK_VERSION}/${SPARK_ARCHIVE}"
RUN tar -xf $SPARK_ARCHIVE
RUN rm $SPARK_ARCHIVE

ENV SPARK_DIR "/spark/spark-${SPARK_VERSION}-bin-hadoop${HADOOP_VERSION}"

EXPOSE 10000

ENTRYPOINT java \
 -Duser.timezone=Etc/UTC \
 -Xmx512m \
 -cp "${SPARK_DIR}/conf:${SPARK_DIR}/jars/*" \
 org.apache.spark.deploy.SparkSubmit \
 --master local[8] \
 --conf spark.executor.extraJavaOptions=-Duser.timezone=Etc/UTC \
 --conf spark.eventLog.enabled=false \
 --class org.apache.spark.sql.hive.thriftserver.HiveThriftServer2 \
 --name "Thrift JDBC/ODBC Server" \
 --executor-memory 512m \
 spark-internal