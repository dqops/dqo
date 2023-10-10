
## get_table_comparison_monitoring_results  
Retrieves the results of the most table comparison performed using the monitoring comparison checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_monitoring_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/monitoring/{timeScale}/comparisons/{tableComparisonConfigurationName}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_results_model](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)||[TableComparisonResultsModel](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)|






___  

## get_table_comparison_partitioned_results  
Retrieves the results of the most table comparison performed using the partitioned comparison checks, comparing days or months of data.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_partitioned_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/partitioned/{timeScale}/comparisons/{tableComparisonConfigurationName}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_results_model](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)||[TableComparisonResultsModel](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)|






___  

## get_table_comparison_profiling_results  
Retrieves the results of the most table comparison performed using the profiling checks comparison checks.  
[Source code](https://github.com/dqops/dqo/blob/develop/distribution/python/dqops/client/api/table_comparison_results/get_table_comparison_profiling_results.py)
  

**GET**
```
api/connections/{connectionName}/schemas/{schemaName}/tables/{tableName}/profiling/comparisons/{tableComparisonConfigurationName}/results  
```

**Return value**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[table_comparison_results_model](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)||[TableComparisonResultsModel](\docs\client\operations\table_comparison_results\#tablecomparisonresultsmodel)|






___  

___  

## TableComparisonResultsModel  
The table comparison result model with the summary information about the most recent table comparison that was performed.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|

___  

