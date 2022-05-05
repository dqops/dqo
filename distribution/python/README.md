DQO.ai Data Quality Observer
============================

DQO.ai is a Data Observability tool that was designed to analyze the quality of databases, data warehouses and data lakes.
What makes DQO.ai unique is extensibility. DQO.ai comes with a built-in list of verified and proven data quality checks
that are verifying the most important DAMA data quality dimensions. 
New data quality checks may be easily added or built-in checks could be customised to meet the needs.
Data quality checks are separated into the sensor part (a Jinja2 template of the SQL query) and a python rule that
can use custom anomaly prediction logic.

Why should you choose DQO.ai over any other Data Observability tool? The first reason is our extensibility that lets you
define business specific data quality checks. 

Our second reason: DQO.ai is DevOps and MLOps friendly! 

We designed DQO.ai after we had to define 6000 custom data quality checks.
DQO.ai stores all definitions in yaml files. You can store the data quality rules along with your data pipeline code or ML code, just check it to Git.
If that is not enough, just open your text editor of choice on any platform (Windows, Linux, MacOS) and edit your data quality
definition files without even looking into the documentation. DQO.ai supports code completion on all mayor text editors (VSCode, PyCharm, Eclipse to name a few).


Installation
------------
All you need to do is just perform:

 ``$ pip install dqoai``

which will install a local DQO.ai agent on your system. 
If you prefer to work with the source code, just clone our GitHub repository [https://github.com/dqoai/dqo](https://github.com/dqoai/dqo)
and run 

 ``$ ./dqo``

on MacOS/Linux or

 ``$ dqo.cmd``

on Microsoft Windows.


Repository
----------
The project is hosted on GitHub. You can look at the source here:

  https://github.com/dqoai/dqo


Documentation
-------------
Our documentation for the most recent release of DQO.ai is hosted here:

  https://doc.dqo.ai/latest
  

Get more from DQO.ai
--------------------
Standalone DQO.ai distributed as a pip package will serve you well. You can register data sources, import source tables
and define data quality requirements for your tables. You can also analyze the quality of your data over the time. 
DQO.ai will maintain a local database that is compliant with Apache Spark, Apache Hive, Google BigQuery and many other platforms
that can read Parquet files structured in Apache Hive partition folders. 

Please register for our free DQO Cloud account at https://cloud.dqo.ai/registration. DQO Cloud will give you additional free benefits:
- store, pull and push your data quality definitions to the cloud
- observe the data quality on dashboards
- find anomalies in your data on charts


Contact and issues
------------------
If you find any issues with the tool, just post the issue here:

  https://github.com/dqoai/dqo/issues

or contact us through our website:

 https://dqo.ai/

Enjoy!