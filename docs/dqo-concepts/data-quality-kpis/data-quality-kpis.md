# Data quality KPIs

The results of data quality measurements are defined as data quality key performance indicators (KPIs). DQO calculates 
data quality KPIs as a percentage of passed [data quality checks](../checks/index.md) for each table, database, or connection.

We can divide the data quality KPIs into business-focused and data engineering-focused. 

- The data quality KPIs for business help monitor data quality to create insights, optimize processes, and improve decision-making.
- The data quality KPIs for data engineering monitor the problems with the data pipelines, file skipping, pipeline failure, etc.

The data model used in DQO tool for storing data quality test results supports reporting various data quality KPIs. DQO 
stores the result for both passed (no alert raised or only a warning raised) and failed (errors or fatal alerts raised)
[data quality checks](../checks/index.md) evaluations. 

[Data quality checks](../checks/index.md) can define the alerting threshold at three severity levels: warning, error and fatal.
The final alert raised by the data quality check evaluation reflects the most severe level for which the threshold has been met.


## Aggregation of data quality KPIs
Data quality KPIs can be aggregated at multiple levels, providing ways to measure the data quality for time periods 
(days, weeks, months, etc.), data quality dimensions, data streams (such as by country), or any combination of these 
grouping levels.

The expected result of calculating the data quality KPI at different grouping levels may look like the tables below.

Data quality KPIs at a day level.

| Date       | KPI value |
|:-----------|----------:|
| 2022-10-01 |     95.1% |
| 2022-10-02 |     96.2% |
| 2022-10-03 |     94.5% |
| 2022-10-04 |     94.7% |

Data quality KPIs at a day and data quality dimension level. 

| Date       | Timelines | Completeness | Validity |
|------------|-----------|--------------|----------|
| 2022-10-01 | 96.1%     | 97.4%        | 95.1%    |
| 2022-10-02 | 99.2%     | 94.6%        | 96.2%    |
| 2022-10-03 | 94.6%     | 97.0%        | 94.3%    |
| 2022-10-04 | 99.1%     | 93.2%        | 94.7%    |

Additionally, data quality KPIs can be calculated for different [data streams](../data-grouping/data-grouping.md)
separately. Data aggregated in a single database (or a data lake) can be loaded from different data sources. 
To calculate a separate data quality KPI for each data source, it must be possible to identify that source at the data level.

[Read more about data stream segmentation](../data-grouping/data-grouping.md)

Data quality KPIs can also be calculated for combinations of data sources (data streams), time periods and data quality
dimensions. An example output of a data quality KPI calculation at a month, country-level data sources, and separate 
data quality dimensions would look like the following table:

|   Month | Data Source | Timelines | Completeness | Validity |
|--------:|:------------|----------:|-------------:|---------:|
| 2022-10 | US          |     96.1% |        97.4% |    95.1% |
| 2022-10 | UK          |     99.2% |        94.6% |    96.2% |
| 2022-10 | FR          |     94.6% |        97.0% |    94.3% |
| 2022-10 | JP          |     99.1% |        93.2% |    94.7% |

## What's next
- Read about  [build-in data quality dashboards](../data-quality-dashboards/data-quality-dashboards.md)
- Learn how to [review results of data quality monitoring results on dashboards](../../working-with-dqo/data-quality-dashboards/data-quality-dashboards.md)
