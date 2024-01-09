# Data quality dimensions

## What is the data quality dimension?

Data quality dimension is a term adopted by the data quality field to identify these aspects of data that can be measured 
and through which its quality can be quantified. While different experts have proposed different data quality dimensions
and there is no standardization of their names or descriptions, almost all of them include some version of accuracy, completeness, 
consistency, timeliness, uniqueness and validity.

### Data quality dimension categories

The following table explains the typical data quality dimensions and data quality issues that can be detected with those dimensions.
The full list of data quality dimensions can be found in [Dimensions of Data Quality (DDQ) Research Paper](https://www.dama-nl.org/wp-content/uploads/2020/09/DDQ-Dimensions-of-Data-Quality-Research-Paper-version-1.2-d.d.-3-Sept-2020.pdf) 
published by DAMA NL Foundation.

| Data quality dimension | Definition                                                                                                                                                                                                                                                                                                          | Potential issues                                                                                                                                                    |
|:-----------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Accuracy**             | The degree of closeness of data values to real values, often measured by comparison with a known source of correct information.                                                                                                                                                                                     | Data cannot be used as a reliable source of information and impacts the organization’s business intelligence, budgeting, forecasting and other critical activities. |
| **Completeness**         | The degree to which all required<br/>- records in the dataset,<br/>- data values<br/>are present with no missing information.<br/>The completeness does not measure accuracy or validity, it measures what information is missing.                                                                                  | Missing values/data or rows.                                                                                                                                        |
| **Consistency**          | The degree to which data values of two sets of attributes<br/>- within a record,<br/>- within a data file,<br/>- between data files,<br/>- within a record at different points in time<br/>comply with a rule.<br/>This dimension represents if the same information stored and used at multiple instances matches. | An inconsistent number of rows. <br/>Information stored in one place does not match relevant data stored elsewhere.                                                 |
| **Reasonableness**       | The degree to which a data pattern meets expectations. Reasonableness measures the degree to which data values have a reasonable or understandable data type and size.                                                                                                                                              | The values in the database are not reasonable.                                                                                                                      |
| **Timeliness**           | The degree to which the period between the time of creation of the real value and the time that the dataset is available is appropriate.                                                                                                                                                                            | The data is not up-to-date.                                                                                                                                         |
| **Uniqueness**           | The degree to which records occur only once in a data file and are not duplicated.                                                                                                                                                                                                                                  | The same data is stored in multiple locations.                                                                                                                      |
| **Validity**             | The degree to which data values comply with pre-defined business rules such as the format, type, and range. E.g. zip codes. e-mails                                                                                                                                                                                 | Invalid data format.                                                                                                                                                |

## Selecting data quality dimensions for monitoring

Selecting data quality dimensions is one of the steps in the process of defining business needs for data quality monitoring.
This is usually done by the Data Owner who understands the purpose of the data, the data model, and the business processes 
in their area of responsibility.

Before you can select data quality dimensions which are relevant for your company, you must first identify current goals 
and scope in terms of data quality monitoring. This will make it easier to come up with the metrics to measure its quality.

Next you need to identify the data elements that are critical or required for a specific business process that needs to 
be monitored. This data is typically referred to as critical data elements (CDEs). The Data Owner should also collect and
define the expectations of data consumers regarding the condition of the data, that ensure its suitability for particular purposes.

It is also a good idea to review the list of previous data quality issues that the Data Owner would like to eliminate in the future.

Only after completing the previous steps you can assess data quality dimensions which are important for your company.
For example, if the data must arrive on time and without delays - the company should prioritize timeliness. If it is more
important that the data arrives in a certain format - the company should prioritize validity.

You can learn more about defining data quality requirements and how to set up the whole data quality monitoring process in our eBook 
["A step-by-step guide to improve data quality"](https://dqops.com/dqo_ebook_a_step-by-step_guide_to_improve_data_quality-2/).