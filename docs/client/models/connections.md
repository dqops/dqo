
## AllChecksPatchParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|[check_model_patch](\docs\client\models\#checkmodel)|Sample configured check model which will pasted onto selected checks.|[CheckModel](\docs\client\models\#checkmodel)|
|selected_tables_to_columns|List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.|Dict[string, string_list]|
|override_conflicts|Override existing configurations if they&#x27;re present. If false, apply updates only to the fields for which no configuration exists.|boolean|


___  

## BulkCheckDisableParameters  
  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|[check_search_filters](\docs\client\models\#checksearchfilters)|Filters addressing basic tree search parameters. These filters takes precedence over other selectors.|[CheckSearchFilters](\docs\client\models\#checksearchfilters)|
|selected_tables_to_columns|List of concrete table and column names which will be the target. Column mappings are ignored for table level checks. This filter is applied at the end.|Dict[string, string_list]|


___  

## CommonColumnModel  
Dictionary model used for combo boxes to select a column. Returns a column name that exists in any table within a connection (source)
 and a count of the column occurrence. It is used to find the most common columns.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|column_name|Column name.|string|
|tables_count|Count of tables that are have a column with this name.|integer|


___  

## ConnectionSpecificationModel  
Connection model returned by the rest api.  
  

**The structure of this object is described below**  
  

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|
|---------------|---------------------------------|-----------|
|connection_name|Connection name.|string|
|connection_hash|Connection hash that identifies the connection using a unique hash code.|long|
|[spec](\docs\reference\yaml\connectionyaml\#connectionspec)|Full connection specification, including all nested objects (but not a list of tables).|[ConnectionSpec](\docs\reference\yaml\connectionyaml\#connectionspec)|
|can_edit|Boolean flag that decides if the current user can update or delete this object.|boolean|


___  

