# Getting started with DQOps
This guide contains a quick tutorial on how to get started with DQOps using the web interface, analyze a data source, and review the data quality results. 

## Sample data
In the example, we will a add connection to the [BigQuery public dataset Austin Crime Data](https://console.cloud.google.com/marketplace/details/city-of-austin/austin-crime). 
Next, we will run and review [Basic statistics](../working-with-dqo/collecting-basic-data-statistics.md), and automatically added profiling and monitoring [data quality checks](../dqo-concepts/definition-of-data-quality-checks/index.md). 
Finally, we will review the data quality results on the [data quality dashboards](../dqo-concepts/types-of-data-quality-dashboards.md). 

!!! note "Google BigQuery is not the only supported data source"

    We are using Google BigQuery in the *getting started* guide and [DQOps use cases](../examples/index.md) because
    Google provides sample datasets for free. You can reproduce all steps shown in this *getting started* guide
    on the same sample data.

    The list of [data sources supported by DQOps](../data-sources/index.md) shows the connection screens to
    analyze data quality of other databases. The steps to connect to a different data source are the same as described in this 
    *getting started* guide.


## Steps

1. [Local installation](installation.md)

2. [Connect to a data source](add-data-source-connection.md)

3. [Review the initial results and run monitoring checks](review-results-and-run-monitoring-checks.md)

4. [Review the results on data quality dashboards](review-results-on-dashboards.md) 

 
