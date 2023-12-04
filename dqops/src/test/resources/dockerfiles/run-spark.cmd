docker build -f spark.Dockerfile -t spark .
@REM docker run -it -p 10000:10000 -p 4040:4040 spark