@REM Starts local airflow with always the latest dqops python client

xcopy ..\..\distribution\ .\distribution\ /E

docker build -t airflow-dqo:latest .

rd /s /q %cd%\distribution 

docker compose up
