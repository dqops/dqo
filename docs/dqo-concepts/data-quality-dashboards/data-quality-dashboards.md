# Data quality dashboards

DQO has multiple built-in data quality dashboards for displaying [data quality KPI](../data-quality-kpis/data-quality-kpis.md).
Our dashboards use [Looker Studio](https://lookerstudio.google.com/overview) (formerly Google Data Studio) business 
intelligence environment. We chose Looker Studio because there is no per-user license fee, which allows granting access
to all parties that might be involved in the issue resolution process.

All data quality results are synchronized to a private data quality data warehouse in the Google Cloud. 
Data quality projects implemented with DQO receive a complementary Looker Studio instance connected to a data quality 
data warehouse. DQO customers can ask the vendor to access a custom Looker Studio data source, providing direct access 
to the data quality lakehouse.

## Groups of dashboards

Data quality dashboards are divided into multiple groups, depending on the audience and purpose of these dashboards. 

- **Governance dashboards**. These dashboards show high-level data quality KPIs, aggregated on a macro scale that should be shared
at a corporate level. With governance dashboards senior management is able to review key data metrics per connection, 
[data quality dimensions](../data-quality-dimensions/data-quality-dimensions.md), [check category](../checks/#categories-of-checks) 
and [data streams](../data-stream-segmentation/data-stream-segmentation.md).
The governance dashboards allows filtering data by time period and previously defined data streams which can represent location, 
business unit, vendor, supplier, or subsidiary.

- **Operational dashboards**. Operational dashboards helps data engineers and data owners to identify areas (tables or 
data pipelines) in a data warehouse or data lake with the highest number of data quality issues that should be addressed. 

- **Detailed dashboards**. This type of data quality dashboard show detailed information at the table level. 
In DQO there are two groups of dashboards in this category: Issue details and Details per category. Issue details group 
focuses on issues grouped by [quality dimensions](../data-quality-dimensions/data-quality-dimensions.md), 
[check types](../checks/#types-of-checks), [check categories](../checks/#categories-of-checks) or tables. Details per 
category groups issues by volume, timeliness and completeness. The detailed dashboards are useful for data engineers and data owners 
to better understand data dynamics during the investigation phase when the data quality issue is being diagnosed and 
later to confirm whether it has been resolved.

- **Technical dashboards**. Technical dashboards groups dashboards with summaries of tests, scoreboard or KPIs per table, 
check or data streams.


