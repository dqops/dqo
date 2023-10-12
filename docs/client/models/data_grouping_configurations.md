
## DataGroupingConfigurationListModel  
Basic model for data grouping configuration on a table, returned by the rest api.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|default_data_grouping_configuration|True when this is the default data grouping configuration for the table.|boolean| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

## DataGroupingDimensionSpec  
Single data grouping dimension configuration. A data grouping dimension may be configured as a hardcoded value or a mapping to a column.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|source|The source of the data grouping dimension value. The default grouping dimension source is a tag. Assign a tag when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|enum|tag<br/>column_value<br/>| | |
|tag|The value assigned to a data quality grouping dimension when the source is &#x27;tag&#x27;. Assign a hardcoded (static) data grouping dimension value (tag) when there are multiple similar tables that store the same data for different areas (countries, etc.). This could be a country name if a table or partition stores information for that country.|string| | | |
|column|Column name that contains a dynamic data grouping dimension value (for dynamic data-driven data groupings). Sensor queries will be extended with a GROUP BY {data group level colum name}, sensors (and alerts) will be calculated for each unique value of the specified column. Also a separate time series will be tracked for each value.|column_name| | | |
|name|Data grouping dimension name.|string| | | |

___  

## DataGroupingConfigurationSpec  
Configuration of the data groupings that is used to calculate data quality checks with a GROUP BY clause.
 Data grouping levels may be hardcoded if we have different (but similar) tables for different business areas (countries, product groups).
 We can also pull data grouping levels directly from the database if a table has a column that identifies a business area.
 Data quality results for new groups are dynamically identified in the database by the GROUP BY clause. Sensor values are extracted for each data group separately,
 a time series is build for each data group separately.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[level_1](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 1 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_2](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 2 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_3](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 3 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_4](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 4 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_5](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 5 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_6](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 6 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_7](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 7 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_8](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 8 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |
|[level_9](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)|Data grouping dimension level 9 configuration.|[DataGroupingDimensionSpec](\docs\reference\yaml\connectionyaml\#datagroupingdimensionspec)| | | |

___  

## DataGroupingConfigurationModel  
Model of data grouping configuration on a table returned by the rest api, including all configuration information.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|connection_name|Connection name.|string| | | |
|schema_name|Schema name.|string| | | |
|table_name|Table name.|string| | | |
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|[spec](\docs\client\models\#datagroupingconfigurationspec)|Data stream specification.|[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

## DataGroupingConfigurationTrimmedModel  
Data grouping on a table model with trimmed access path.  
  

**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_grouping_configuration_name|Data grouping configuration name.|string| | | |
|[spec](\docs\client\models\#datagroupingconfigurationspec)|Data grouping configuration specification.|[DataGroupingConfigurationSpec](\docs\client\models\#datagroupingconfigurationspec)| | | |
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean| | | |

___  

