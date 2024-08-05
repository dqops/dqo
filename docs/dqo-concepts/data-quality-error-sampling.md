---
title: Data quality error sampling
---
# How to configure and use data quality error sampling in DQOps
Read this guide to understand how to configure and use error sampling within DQOps to gain valuable insights into data quality issues.

## Overview
Error sampling in DQOps capture representative examples of data that fail to meet specified data quality criteria.
These samples are stored and can be subsequently analyzed to identify the root causes of data quality issues.
Error sampling applies only to column-level checks, but not for all. For example, null checks are generally excluded from error sampling,
except for the null_count and null_percent checks.

## Configuring an ID column
To be able to collect error samples you need to define an ID column to uniquely identify error samples.
Many database systems inherently include ID columns (such as auto-incrementing fields in SQL Server or MySQL). 
In that case, DQOps automatically detects available ID columns during the data import process.

In cases where ID columns are not present in the database, you can specify them manually in DQOps. You have the option
to select up to nine columns from the available list to serve as ID columns.

To specify the ID column, navigate to the **Data Source** section, select the column which you would like to set as ID column,
click on the check mark next to the **Is a unique identifier** option and click the **Save** button in hte upper right corner.. 

![Configuring an ID column in DQOps ](https://dqops.com/docs/images/concepts/data-quality-error-sampling/configuring-id-column.png){ loading=lazy; width="1200px" }

A "key" icon will appear next to columns name on the column summary.

![Column summary with configured ID column](https://dqops.com/docs/images/concepts/data-quality-error-sampling/column-summary-with-configured-id-column.png){ loading=lazy; width="1200px" }

## Configuring error sample collection
DQOps collects error samples by default when profiling checks are executed or when checks are run manually from the Check editor, not through scheduled jobs or the navigation tree.
This behavior can be controlled at both the table and check levels.

### **Table-level error sample configuration**
To control error sample collection at the table level, follow these steps:

1. Navigate to the **Data Sources** section.
2. Select the desired table from the tree view on the left.
3. Check or uncheck the following options:
     * **Do not collect error samples for profiling checks:** This disables automatic error sample collection for profiling checks on the selected table.
     * **Always collect error samples for scheduled monitoring checks:** This enables automatic error sample collection for scheduled monitoring checks on the selected table.

![Table-level error sample configuration](https://dqops.com/docs/images/concepts/data-quality-error-sampling/table-level-error-sample-configuration.png){ loading=lazy; width="1200px" }

### **Check-level error sample configuration**
For more detailed control, you can configure error sample collection at the check level:

1. Navigate to the **Profiling checks**, **Monitoring checks** or **Partition checks** section.
2. Select the desired column from the tree view on the left.
3. Navigate to the Check Editor.
4. Locate the desired check.
5. Click the **Settings** icon next to the check name.
6. Check or uncheck the **Always collect error samples** option within the check settings.

![Check-level error sample configuration](https://dqops.com/docs/images/concepts/data-quality-error-sampling/check-level-error-sample-configuration.png){ loading=lazy; width="1200px" }

### **Check patterns-level error sample configuration**
DQOps allows you to create reusable configurations for data quality checks, known as **check patterns**. 
These patterns can inherit error sample collection settings, ensuring consistent behavior for imported tables and columns. 
Refer to the dedicated guide to learn more about [Data quality check patterns](./data-observability.md#data-quality-check-patterns).

This section outlines the steps to configure error sample collection at the check level within a check pattern:

![Navigating to the check patterns level error sample configuration](https://dqops.com/docs/images/concepts/data-quality-error-sampling/navigation-to-check-patterns-level-error-sample-configuration.png){ loading=lazy; width="1200px" }

1. Navigate to the **Configuration** section.
2. Select the **Default check configuration** > **Column-level checks patterns** option from the tree view on the left.
3. Choose the Check pattern you want to modify.
4. Select the **Profiling checks**, **Monitoring checks** or **Partition checks** tab.
5. Locate the desired check.
6. Click the **Settings** icon next to the check name.
7. Check or uncheck the **Always collect error samples** option within the check settings.

![Check patterns level error sample configuration](https://dqops.com/docs/images/concepts/data-quality-error-sampling/check-patterns-level-error-sample-configuration.png){ loading=lazy; width="1200px" }

By configuring error sample collection at the check pattern level, you can ensure consistent
data quality analysis across your imported data sources.


## Collecting and viewing error samples using the user interface
Error samples are collected automatically for Profiling checks and for other check types only when checks are run manually 
from the Check editor, not through scheduled jobs or the navigation tree.

To collect error samples, just run the check manually:

1. Open the Check editor of the **Profiling checks**, **Monitoring checks** or **Partition checks**.
2. Select the column of interest from the tree view on the left.
3. Activate the check of interest, set the thresholds or leave the default options.
4. Click on the check Run icon.

![Collect error samples](https://dqops.com/docs/images/concepts/data-quality-error-sampling/collect-error-samples.png){ loading=lazy; width="1200px" }

To view the error samples, click on the **Results** icon, and select the tab **Error sampling**. 
For column-level checks that do not have error samples, the tab will be inactive.  

![View error samples](https://dqops.com/docs/images/concepts/data-quality-error-sampling/view-error-samples.png){ loading=lazy; width="1200px" }

The table with error samples has the following columns:

- **Sample index:** The index of the collected sample.
- **Collected at:** Column for the time when the error samples were captured. All error samples results started as part of the same error sampling session will share the same time.
- **Result:** The sample value.
- **Result data type:** The sample's result data type.
- **ID Column 1:** The value of the ID column from the original data row that corresponds to the error sample. This column helps trace the error back to its source record. You can set up to 9 ID Columns.
- **Data grouping:** The data grouping name
- **Id:** The error sample result id (primary key). This value identifies a single row.

## Error sampling result storage
Error samples collected by DQOps are stored in a Parquet table named `error_samples`. 
This table resides within the **[$DQO_USER_HOME]/.data/error_samples folder**, leveraging uncompressed Parquet files for efficient data storage.

For a detailed breakdown of the `error_samples` table schema and its columns, please refer to the dedicated documentation:
[error samples Parquet table schema](../reference/parquetfiles/error_samples.md).

## What's next

- Learn how the [table metadata](configuring-table-metadata.md) is stored in DQOps.
- If you want to see how to import the metadata of data sources using DQOps user interface, 
  go back to the *getting started* section, and read the [adding data source connection](../getting-started/add-data-source-connection.md) again.
- Learn how to [configure data quality checks and rules](configuring-data-quality-checks-and-rules.md) in the *.dqotable.yaml* files.
- Learn more about managing configuration in the [`DQOps user home` folder](dqops-user-home-folder.md).
- Review the list of [data sources supported by DQOps](../data-sources/index.md) to find a step-by-step configuration manual for each data source.
- Learn what extensions are needed to activate editing DQOps configuration files in
  [Visual Studio Code with code completion and validation](../integrations/visual-studio-code/index.md).