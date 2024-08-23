---
title: Data quality rule mining
---
# Data quality rule mining
Read this guide to learn how DQOps automatically configures (mines) the rule thresholds in data quality checks to find the most common data quality issues.

## Definition
Data quality rule mining automatically configures data quality checks, focusing on finding the most probable data quality issues.

### What is data quality rule mining
Setting up data quality rules by hand and choosing the right threshold can take a lot of time. 
This is especially true because most of the time, data quality checks will show that everything is okay.
The real reason we set up these checks is to find those few data quality problems that are hard to see just by looking at the data.

DQOps makes this process automatic with its automated data quality rule mining engine.
This engine analyzes both [statistical summaries](../working-with-dqo/collecting-basic-data-statistics.md) of the monitored table and sample values from the columns.
You can specify a desired percentage of rows that can contain invalid records.
DQOps will then examine the sample data and propose configurations for data quality checks that can detect the most common types of issues. 
These might include a few duplicate rows in a large table, or some emails that don't match a well-known email format.

Besides configuring checks to find existing problems, DQOps also proposes checks that will pass for the current data. 
These checks serve as a baseline. If the data drifts over time and its distribution changes, these checks will fail, 
indicating that the data no longer resembles its structure at the time of profiling.

### Data profiling stages
DQOps uses a two-step process to check your data, which is more thorough than the usual way described in books. 
Typically, data profiling just gathers [basic data statistics](../working-with-dqo/collecting-basic-data-statistics.md)
and some sample values from a table and its columns. The tool then shows you things like how many missing or duplicate values there are,
and you have to decide if that's okay and set up the data quality checks yourself.

DQOps collects basic statistics automatically for every table you register in DQOps. 
But it also has a second step that's more advanced. It actually tests your data with different checks to find the most common data quality issues.
You can do this step a few ways: by hand using the [data quality check editor](dqops-user-interface-overview.md#check-editor),
semi-automatically by enabling data quality checks for all matching tables and columns using [data quality policies](data-observability.md),
or you can let DQOps suggest the configuration of [data quality checks](definition-of-data-quality-checks/index.md) for you and then review the problems it finds in your data.

## Information used for proposing checks
DQOps uses all information collected during basic profiling to propose a reasonable configuration of data quality checks. The data sources are described below.


### Basic data statistics
The data quality rule miner uses the following data captured as basic statistics to configure data quality rule thresholds.

* [Table volume](../categories-of-data-quality-checks/how-to-detect-data-volume-issues-and-changes.md) to configure the minimum accepted row count.

* [Column counts](../categories-of-data-quality-checks/how-to-detect-table-schema-changes.md) to configure the expected column count and detect when it changes.

* The [range of values in numeric columns](../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-numeric-fields.md),
  especially the minimum, maximum, and mean (average) values. 
  DQOps will set up data quality checks to detect when values out of the range appear in the table.

* The range of date and timestamp columns. DQOps will detect obviously invalid dates, such as *1970-01-01* or *2099-12-31*,
  which are often used as placeholder values for null values. The most recent date found in the column used to configure [data timeliness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)
  (the timestamp of an event or transaction) will be used to configure a maximum delay in [data freshness checks](../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md#data-freshness).

* The [text length](../categories-of-data-quality-checks/how-to-detect-data-quality-issues-in-text-fields.md)
  statistics of text columns, such as the minimum, maximum, and mean length of text columns. 
  Similarly to numeric columns, DQOps will configure checks that will detect text values that are too short or too long in the future.

* The minimum and maximum [word count of text columns](../checks/column/text/min-word-count.md),
  which can provide a helpful way to analyze the quality of data used for Generative AI use cases.

* [Detected data types](../categories-of-data-quality-checks/how-to-detect-data-type-changes.md) in text columns containing, 
  if the column contains only boolean, numeric, or date values. The check configured by the DQOps rule miner can be used to analyze raw data in landing zones.

* [Data completeness](../categories-of-data-quality-checks/how-to-detect-empty-or-incomplete-columns-with-nulls.md) statistics, 
  such as the number of null values in a column. When a column contains just a few null values, 
  and their count is below the desired percentage of error records, DQOps will configure the data quality checks to raise data completeness issues for these columns.

* [Data uniqueness](../categories-of-data-quality-checks/how-to-detect-data-uniqueness-issues-and-duplicates.md) statistics for each column.
  DQOps will use this information to configure duplicate detection checks for columns containing just a few duplicate values 
  that must be invalid or configure the distinct count checks to monitor the number of distinct values to stay in range.


### Data samples
DQOps configures several data quality checks using sample values captured from each column.
The types of data quality checks configured by the rule mining engine are listed below.

  * DQOps captures the top 100 most common values in each column. If the number of distinct values is below 100 and
    the captured sample values cover all possible values in a column, DQOps will configure the 
    [values_found_in_set_percent](../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md#how-to-validate-accepted-values-in-columns) dictionary checks.
    If some values captured in the data samples are rare and present in less than the expected percentage of
    erroneous rows (the default is 2% of records), DQOps will not include these values in the list of accepted values.
    Running the data quality checks in the "accepted values" category will generate data quality issues, and the error samples will show these invalid values.

    When a data dictionary containing valid values is already defined in DQOps, and the majority of column values are present in any of 
    the [data dictionaries](../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md#defining-data-dictionaries) defined by the user,
    the rule mining engine will configure the "values in set" checks to use that dictionary. 
    This algorithm will configure data quality checks to detect values not present in the dictionary.

  * The sample values are also used to configure the [expected_texts_in_top_values](../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md#testing-the-most-common-values) checks,
    ensuring that the most common values are always the same in the data set over time.

  * Text values are analyzed for well-known patterns, such as [UUID identifiers](../examples/data-validity/percentage-of-valid-uuid.md),
    [emails](../examples/data-validity/detect-invalid-emails.md), phone numbers, [ISO country codes](../examples/data-validity/percentage-of-valid-currency-codes.md),
    or [currency codes](../categories-of-data-quality-checks/how-to-validate-accepted-values-in-columns.md#validating-country-codes).
    When DQOps detects columns mainly containing these values, relevant pattern validation checks are automatically configured.

  * DQOps will detect column values containing possible [patterns that can be analyzed with regular expressions](../categories-of-data-quality-checks/how-to-detect-bad-values-not-matching-patterns.md#validating-custom-regular-expressions). 
    Suppose the sample column values contain texts of at least three components, such as numbers, texts, and special characters, such as "A/2024/11-C".
    In that case, DQOps will propose a regular expression pattern that will detect values that do not match the pattern.


### Results of data profiling checks
The rule mining engine is not limited to data quality checks that can be configured using sample values or data statistics.
Users can use the [data observability patterns](data-observability.md) (data quality policies) to configure
[data profiling checks](definition-of-data-quality-checks/data-profiling-checks.md) without any [data quality rules](definition-of-data-quality-rules.md) enabled.
These checks will only capture the [sensor readouts](../reference/parquetfiles/sensor_readouts.md) (the data quality metrics) without evaluating them with [data quality rules](definition-of-data-quality-rules.md) .

For example, the user can enable all profiling checks in the 
[PII (Personal Identifiable Information) check category](../categories-of-data-quality-checks/how-to-detect-pii-values-and-sensitive-data.md)
to detect columns containing a few sensitive values, such as emails or phone numbers inside comment columns. 
When the rule mining engine notices that the [contains_email_percent](../checks/column/pii/contains-email-percent.md)
check finds 1% of records containing emails and the desired error rate is 2%, 
DQOps will configure the [contains_email_percent](../checks/column/pii/contains-email-percent.md) check with a 
[max_percent](../reference/rules/Comparison.md#max-percent) threshold 0%, identifying all these records as values containing a possible data leak.

DQOps rule miner also supports custom data quality checks as long as they use built-in data quality rules, such as the [max_percent](../reference/rules/Comparison.md#max-percent) rule.

The data quality results of the profiling checks are used further in DQOps when activating vetted data quality checks for continuous data quality monitoring
in the [monitoring](definition-of-data-quality-checks/data-observability-monitoring-checks.md) or [partition](definition-of-data-quality-checks/partition-checks.md) sections.


## Data quality maturity stages
``` mermaid
graph LR
  DATA_QUALITY_ASSESSMENT[<strong>Data quality<br/>assessment</strong>]:::assessment --> |Identify issues<br/>and required<br/>data quality checks| DATA_QUALITY_REVIEW[<strong>Review data<br/> quality issues</strong><br/>with data owners]:::review;
  DATA_QUALITY_REVIEW --> |Valid issues<br/>confirmed| DATA_CLEANSING[<strong>Data cleansing<br/></strong> to fix data<br/>quality issues]:::cleansing;
  DATA_CLEANSING --> |Activate<br/>data observablity| DATA_OBSERVABILITY[<strong>Data quality<br/>monitoring</strong>]:::monitoring;
  
  classDef assessment fill:#57ff6a;
  classDef review fill:#57e3ff;
  classDef cleansing fill:#5795ff;
  classDef monitoring fill:#fccf86;
```

The whole data quality lifecycle process covers all steps, from learning about the structure of a table, selecting and evaluating data quality checks, reviewing detected data quality issues,
fixing these issues by data cleansing, and enabling long-term data quality monitoring to detect when the issue reappears instantly.


### Data quality assessment
``` mermaid
graph LR
  BASIC_PROFILING[Basic statistics<br/>collection]:::step --> |Propose data<br/>quality checks| RULE_MINING[Data quality<br/>rule mining]:::step;
  RULE_MINING --> |Run proposed<br/>checks| ASSESSMENT_RESULTS_REVIEW[Review initial<br/>data quality KPI]:::step;
  ASSESSMENT_RESULTS_REVIEW --> |Correct data<br/>quality rules| DISABLE_FALSE_CHECKS[Disable false-positive<br/>checks and tweak rules]:::step;
  DISABLE_FALSE_CHECKS --> |Share data quality<br/>status report| CONSULT_DQ_ISSUES[Review data quality<br/>issues with the data owner]:::step;
  
  classDef step fill:#57ff6a;
```

The purpose of performing a data quality assessment of a table is to understand its structure and data distribution
and find data quality issues. Data teams that will be using a dataset to ingest it into their data platform or data consumers,
such as data analysts or data scientists, can perform the data quality assessment to verify if the dataset is usable
for their use cases and how much data cleansing and transformation is required to make the data usable.

Data quality assessment is a routine activity for data governance and quality teams. 
DQOps makes this process simpler and possible from a local computer without setting up any complex data quality platforms.
The data quality assessment of tables is performed in the data profiling module in DQOps.


!!! tip "DQOps data quality rule mining is a must-have tool for every software company dealing with data and AI projects"

    If you are starting an AI or Data & Analytics project for a customer, and the data quality is a concern, it should be measured in the project's first stage.
    As soon as you have access to the customer's database or data lake, you should start DQOps locally on your laptop in a new empty folder, 
    import the metadata of the tables, and let the rule mining engine find the most prominent data quality issues of all the tables that you will be using in the project.

    If you see any data quality issue that can pose a severe risk to the delivery of an AI or analytics project,
    use the table quality status screen to show the customer the data health status and request an additional budget
    for performing data cleansing before the poor data quality will affect the project's delivery timeline and goals.


### Review data quality issues
``` mermaid
graph LR
  EXPORT_DATA_QUALITY_REPOT[Export data<br/>quality report]:::step --> |Send issue report<br/>for review| REVIEW_ISSUES[Review and confirm<br/>data quality issues<br/>by data owner]:::step;
  REVIEW_ISSUES --> |Receive confirmation<br/> of valid issues| PLAN_DATA_CLEANSING[Plan data cleansing<br/>and tune data quality rules]:::step;
  
  classDef step fill:#57e3ff;
```

After performing the data quality assessment, the user will identify data quality issues that should be fixed. The data quality issues will fall into two categories:

* Invalid data in the data sources, which requires the engagement of the data source's owner to fix.
  For example, the data must be fixed in a line-of-business application (i.e., CRM, ERP).

* Data transformation issues that can be corrected in the data pipeline.

The tasks for fixing confirmed data quality issues should be planned for the data cleansing tasks.
False-positive issues will require disabling incorrectly configured data quality checks.

### Data cleansing
``` mermaid
graph LR
  REVIEW_ERROR_SAMPLES[Review<br/>error samples]:::step --> |Send errors<br/>to data owner| FIX_IN_DATA_SOURCE[Fix data quality issues<br/>in the data source]:::step;
  FIX_IN_DATA_SOURCE --> |Issues fixed<br/>in the data source| FIX_DATA_PIPELINES[Fix transformations<br/>in data pipelines]:::step; 
  FIX_DATA_PIPELINES --> |Data<br/>fixed| VALIDATE_WITH_CHECKS[Validate fixes<br/>with data quality checks]:::step;
  
  classDef step fill:#5795ff;
```

The responsibility of fixing data cleansing tasks lies with the business platform owners, who are equipped to address issues in line-of-business applications.
The issues that can be fixed in the data pipelines should be fixed by updating the data transformation logic in the data pipelines. Data engineering teams perform this activity.

After the data is fixed, the data profiling checks previously configured in DQOps should be rerun to confirm that the data quality issues were fixed.


### Data quality monitoring
``` mermaid
graph LR
  MINE_MONITORING_CHECKS[Configure<br/>monitoring checks<br/>using rule miner]:::step --> |Full table-scan<br/>checks configured| CONFIGURE_CRON_SCHEDULES[Configure CRON<br/>schedules]:::step;
  MINE_PARTITION_CHECKS[Configure<br/>partition checks<br/>using rule miner]:::step --> |Partition checks<br/>configured| CONFIGURE_CRON_SCHEDULES;
  CONFIGURE_CRON_SCHEDULES --> |Issues detected<br/>by data<br/>observability| ISSUE_DETECTED[Issue detected<br/>and data quality<br/>incident created]:::step;
  ISSUE_DETECTED --> |Send<br/>notification| REVIEW_AND_CONFIRM_ISSUE[Review and<br/>acknowledge<br/>new incidents]:::step;
  REVIEW_AND_CONFIRM_ISSUE --> |Incident<br/>acknowledged| FIX_DATA[Fix data<br/>or data pipelines]:::step;
  FIX_DATA --> |Issues<br/>fixed| REVALIDATE_DATA_WITH_CHECKS[Revalidate data<br/>with data<br/>quality checks]:::step;
  
  classDef step fill:#fccf86;
```

As soon as a valid set of data profiling checks is selected and false-positive data quality checks are disabled, the user is ready to activate continuous data quality monitoring.

DQOps supports two types of continuous data quality monitoring checks:

  * [Monitoring checks](definition-of-data-quality-checks/data-observability-monitoring-checks.md) perform full-table scans in daily or monthly periods.
  * [Partition checks](definition-of-data-quality-checks/partition-checks.md) evaluate the quality of each daily or monthly partition for append-only or huge tables.

These two types of checks are configured in the *Monitoring* and *Partition* sections of the [DQOps user interface](dqops-user-interface-overview.md). 
DQOps will evaluate the configured data quality checks and raise [data quality incidents](grouping-data-quality-issues-to-incidents.md#incident-management) when data quality issues are detected. 
Data operations or support teams will be notified by email or by [incident notifications](grouping-data-quality-issues-to-incidents.md#incident-notifications).


## Data quality check configuration steps
Follow these steps to configure the data quality lifecycle process in DQOps.

### Basic profiling

### Advanced profiling

### Data quality check selection

### Data quality check evaluation

### Data quality health review

### Data quality issue review

### Data quality rule tuning

### Disabling false-positive checks


## Data observability

### Configuring monitoring checks

### Configuring partition checks

### Incident management


## What's next
