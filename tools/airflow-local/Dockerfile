FROM apache/airflow:2.7.1

RUN pip install --upgrade pip

# current requirements contains libraries used by dqops,
# they are installed earlier for faster execution of following docker layers
COPY requirements.txt /
RUN pip install --no-cache-dir "apache-airflow==${AIRFLOW_VERSION}" -r /requirements.txt

COPY distribution/ /dqops/distribution

USER 0
RUN sudo chmod -R 777 /dqops/distribution
USER ${AIRFLOW_UID}

RUN pip install -e /dqops/distribution/python
