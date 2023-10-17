# Local airflow

To run airflow docker locally you will need a Docker Desktop on Windows.

The local setup is based on a docker-compose.yaml which builds a airflow eco-system.
The official airflow image has been extended by dqops python distribution from the local project, which is the recomended way to install and use custom python packages in the airflow image.
This availables to do a developemnt work on the latest, not published python client.

To run the environment, use **run-local-airflow.bat** script.
The script copies the distribution folder for build time to make python files available to Docker context.
Next the build is created and environment is run.

When ready, airflow is available via web browser on an url: **http://localhost:8080/**

## Airflow files

Here are additional folder that are mounted to the airflow.
This means if you want to create or modify a dag, use the dags folder.



