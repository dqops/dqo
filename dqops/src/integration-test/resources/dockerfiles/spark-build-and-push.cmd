@REM Remember to login to the dockerhub first as "dqops" user name.
docker build -f spark.Dockerfile -t dqops/spark:latest .
docker image push dqops/spark:latest